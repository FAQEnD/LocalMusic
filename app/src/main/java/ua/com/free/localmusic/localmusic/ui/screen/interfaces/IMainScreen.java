package ua.com.free.localmusic.localmusic.ui.screen.interfaces;

import java.util.List;

import ua.com.free.localmusic.models.Song;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public interface IMainScreen extends IScreen {

    void updateData(List<Song> songs);

    void notifyUser(String msg);

}
