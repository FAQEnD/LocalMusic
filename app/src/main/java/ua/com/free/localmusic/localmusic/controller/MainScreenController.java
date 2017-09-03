package ua.com.free.localmusic.localmusic.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.api.services.youtube.model.SearchResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.free.localmusic.events.OnSetPlaylistAndPlayEvent;
import ua.com.free.localmusic.localmusic.controller.base.BaseScreenController;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.localmusic.manager.IMediaPlayerManager;
import ua.com.free.localmusic.localmusic.manager.impl.MediaPlayerService;
import ua.com.free.localmusic.localmusic.ui.screen.MainScreen;
import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;
import ua.com.free.localmusic.models.Song;
import ua.com.free.localmusic.utils.ConvertUtils;
import ua.com.free.localmusic.youtube.YoutubeAPI;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public class MainScreenController extends BaseScreenController<IMainScreen> implements IMainScreenController {

    private static final String TAG = MainScreenController.class.getSimpleName();

    private YoutubeAPI mYoutubeAPI;
    private IMediaPlayerManager mMediaPlayerManager;

    public MainScreenController(YoutubeAPI youtubeAPI,
                                IMediaPlayerManager mediaPlayerManager) {
        mYoutubeAPI = youtubeAPI;
        mMediaPlayerManager = mediaPlayerManager;
    }

    @Override
    public void onCreate(Context context) {
        mMediaPlayerManager.setOnErrorListener(pos -> {
            screen.notifyUser("Can't play this track");
            screen.resetPlayPauseButton(pos);
        });
    }

    @Override
    public void askToSearchData(String query) {
        Log.i(TAG, "asked to search data: " + query);
        mYoutubeAPI.search(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::notifyDataChanged)
                .doOnError(this::notifyOnError)
                .onErrorReturn(throwable -> new ArrayList<>())
                .subscribe();
    }

    @Override
    public void askToPlaySong(int pos) {
        Log.i(TAG, "asked to play song with pos: " + pos);
        mMediaPlayerManager.playSong(pos);
    }

    @Override
    public void askToUpdatePlaylist(List<Song> playlist) {
        mMediaPlayerManager.setPlaylist(playlist);
    }

    @Override
    public void askToUpdatePlaylistAndPlay(List<Song> playlist, int position) {
        EventBus.getDefault().post(new OnSetPlaylistAndPlayEvent(playlist, position));
    }

    @Override
    public void startMediaPlayerService(Context context, ArrayList<Song> songs, int positions) {
        Intent serviceIntent = new Intent(context, MediaPlayerService.class);
        serviceIntent.putParcelableArrayListExtra(MediaPlayerService.PLAYLIST_KEY, songs);
        serviceIntent.putExtra(MediaPlayerService.POSITION_KEY, positions);
        context.startService(serviceIntent);
    }

    @Override
    public void askToPlayNextSong() {
        mMediaPlayerManager.playNext();
    }

    @Override
    public int getCurrentTrackPosition() {
        if (!mMediaPlayerManager.isPlaying()) {
            return -1;
        }
        return mMediaPlayerManager.getCurrentTrackPos();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayerManager.isPlaying();
    }

    private void notifyDataChanged(List<SearchResult> results) {
        Log.d(TAG, "going to notify view with result: " + results.toString());
        List<Song> songs = new ArrayList<>(results.size());
        for (SearchResult searchResult : results) {
            songs.add(ConvertUtils.convertToSong(searchResult));
        }
        screen.updateData(songs);
    }

    private void notifyOnError(Throwable throwable) {
        Log.e(TAG, "going to notify user with error: " + throwable.getLocalizedMessage());
        screen.notifyUser(throwable.getLocalizedMessage());
    }
}
