package ua.com.free.localmusic.localmusic.manager;

import ua.com.free.localmusic.models.Song;

/**
 * @author Anton Musiienko on 7/30/2017.
 */

public interface IPlaylist {

    Song getCurrent();

    Song getNext();

    Song getPrevious();

    void onNext();

    void onPrevious();

    void seekTo(int position);

    int getCurrentPosition();

    int getId();

}
