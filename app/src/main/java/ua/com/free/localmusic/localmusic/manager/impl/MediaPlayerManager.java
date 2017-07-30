package ua.com.free.localmusic.localmusic.manager.impl;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.free.localmusic.api.youtuberipper.YoutubeRipperAPI;
import ua.com.free.localmusic.localmusic.manager.IMediaPlayerManager;
import ua.com.free.localmusic.localmusic.manager.IPlaylist;
import ua.com.free.localmusic.models.Song;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;

/**
 * @author Anton Musiienko on 7/30/2017.
 */

public class MediaPlayerManager implements IMediaPlayerManager, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    private static final String TAG = "MediaPlayerManager";

    private MediaPlayer mMediaPlayer;
    private IPlaylist mPlaylist;
    private YoutubeRipperAPI mRipperAPI;

    public MediaPlayerManager(YoutubeRipperAPI ripperAPI) {
        mRipperAPI = ripperAPI;
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
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

    private void playRemoteTrack(Song song) {
        if (song == null) {
            Log.e(TAG, "song is NULL");
            return;
        }
        mRipperAPI.getSongMetadata(song.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::playSongInternal)
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .onErrorReturn(throwable -> null)
                .subscribe();
    }

    private void playSongInternal(SongFromRipperServiceModel song) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(song.getLink());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPlaylist(List<Song> songs) {
        IPlaylist playlist = new Playlist(songs);
        if (mPlaylist != null && mPlaylist.getId() == playlist.getId()) {
            return;
        }
        mPlaylist = playlist;
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
        return true;
    }
}
