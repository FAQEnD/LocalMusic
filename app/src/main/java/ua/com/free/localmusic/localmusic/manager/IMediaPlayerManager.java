package ua.com.free.localmusic.localmusic.manager;

import java.util.List;

import ua.com.free.localmusic.models.Song;

/**
 * @author Anton Musiienko on 7/30/2017.
 */

public interface IMediaPlayerManager {

    interface OnErrorListener {

        void onError(int pos);

    }

    void playSong(int pos);

    void setPlaylist(List<Song> playlist);

    void playNext();

    boolean isPlaying();

    int getCurrentTrackPos();

    void setOnErrorListener(OnErrorListener onErrorListener);

}
