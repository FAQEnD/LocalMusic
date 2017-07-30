package ua.com.free.localmusic.localmusic.controller.interfaces;

import android.content.Context;

import java.util.List;

import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;
import ua.com.free.localmusic.models.Song;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public interface IMainScreenController extends IScreenController<IMainScreen> {

    void onCreate(Context context);

    void askToSearchData(String query);

    void askToPlaySong(int pos);

    void askToUpdatePlaylist(List<Song> playlist);

    void askToPlayNextSong();

    int getCurrentTrackPosition();

    boolean isPlaying();

}
