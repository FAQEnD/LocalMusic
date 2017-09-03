package ua.com.free.localmusic.localmusic.manager.impl;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.free.localmusic.LocalMusicApplication;
import ua.com.free.localmusic.api.youtuberipper.YoutubeRipperAPI;
import ua.com.free.localmusic.events.OnPlayNextEvent;
import ua.com.free.localmusic.events.OnPlaySongEvent;
import ua.com.free.localmusic.events.OnSetPlaylistAndPlayEvent;
import ua.com.free.localmusic.events.OnSetPlaylistEvent;
import ua.com.free.localmusic.localmusic.manager.IMediaPlayerManager;
import ua.com.free.localmusic.localmusic.manager.IPlaylist;
import ua.com.free.localmusic.models.Song;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;

/**
 * Created by Dmitry Risovanyi
 * on 30.08.2017.
 */

public class MediaPlayerService extends Service implements IMediaPlayerManager, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    public static final String PLAYLIST_KEY = "playlist_key";
    public static final String POSITION_KEY = "position_key";

    private static final String TAG = "MediaPlayerService";

    @Inject
    YoutubeRipperAPI ripperAPI;

    private MediaPlayer mMediaPlayer;
    private IPlaylist mPlaylist;
    private IMediaPlayerManager.OnErrorListener mOnErrorListener;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocalMusicApplication.getAppComponent().inject(this);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getParcelableArrayListExtra(PLAYLIST_KEY) != null) {
            setPlaylist(intent.getParcelableArrayListExtra(PLAYLIST_KEY));
            playSong(intent.getIntExtra(POSITION_KEY, 0));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe
    public void onPlaySongEvent(OnPlaySongEvent event) {
        playSong(event.position);
    }

    @Override
    public void playSong(int pos) {
        if (mPlaylist.getCurrentPosition() != pos) {
            mPlaylist.seekTo(pos);
            playRemoteTrack(mPlaylist.getCurrent());
        } else {
            togglePlayer();
        }
    }

    private void togglePlayer() {
        if (isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    @Subscribe
    public void onSetPlaylistAndPlay(OnSetPlaylistAndPlayEvent event) {
        setPlaylist(event.playlist);
        playSong(event.position);
    }

    private void playRemoteTrack(Song song) {
        if (song == null) {
            Log.e(TAG, "song is NULL");
            return;
        }
        ripperAPI.getSongMetadata(song.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::playSongInternal)
                .doOnError(this::notifyUI)
                .onErrorReturn(throwable -> new SongFromRipperServiceModel())
                .subscribe();
    }

    private void playSongInternal(SongFromRipperServiceModel song) {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(song.getLink());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void notifyUI(Throwable t) {
        Log.e(TAG, t.getMessage());
        mMediaPlayer.reset();
        mOnErrorListener.onError(getCurrentTrackPos());
    }

    @Subscribe
    public void onSetPlaylistEvent(OnSetPlaylistEvent event) {
        setPlaylist(event.playlist);
    }

    @Override
    public void setPlaylist(List<Song> songs) {
        IPlaylist playlist = new Playlist(songs);
        if (mPlaylist != null && mPlaylist.getId() == playlist.getId()) {
            return;
        }
        mPlaylist = playlist;
    }

    @Subscribe
    public void onPlayNextEvent(OnPlayNextEvent event) {
        playNext();
    }

    @Override
    public void playNext() {
        Log.d(TAG, "going to play next song");
        mPlaylist.onNext();
        playRemoteTrack(mPlaylist.getCurrent());
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getCurrentTrackPos() {
        return mPlaylist.getCurrentPosition();
    }

    @Override
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        mOnErrorListener = onErrorListener;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "media player completed to play song");
        playNext();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "media player ready");
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError on media player was called. What: " + what + " extra: " + extra);
        mp.reset();
        playRemoteTrack(mPlaylist.getCurrent());
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
