package ua.com.free.localmusic.localmusic.controller;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.free.localmusic.api.youtube.YoutubeAPI;
import ua.com.free.localmusic.api.youtuberipper.YoutubeRipperAPI;
import ua.com.free.localmusic.localmusic.controller.base.BaseScreenController;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;
import ua.com.free.localmusic.models.Song;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;
import ua.com.free.localmusic.utils.ConvertUtils;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public class MainScreenController extends BaseScreenController<IMainScreen> implements IMainScreenController {

    private static final String TAG = MainScreenController.class.getSimpleName();

    private YoutubeAPI mYoutubeAPI;
    private YoutubeRipperAPI mRipperAPI;

    public MainScreenController(YoutubeAPI youtubeAPI, YoutubeRipperAPI ripperAPI) {
        mYoutubeAPI = youtubeAPI;
        mRipperAPI = ripperAPI;
    }

    @Override
    public void onCreate(Context context) {

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
    public void askToDownloadSong(String id) {
        Log.i(TAG, "asked to download data with id: " + id);
        mRipperAPI.getSongMetadata(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::playSong)
                .doOnError(throwable -> Log.e(TAG, throwable.getMessage()))
                .onErrorReturn(throwable -> null)
                .subscribe();

    }

    private void playSong(SongFromRipperServiceModel song) {
        Log.d(TAG, "song metadata: " + song);
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(song.getLink());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
