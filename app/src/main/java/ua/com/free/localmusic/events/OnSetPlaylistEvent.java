package ua.com.free.localmusic.events;

import java.util.List;

import ua.com.free.localmusic.models.Song;

/**
 * Created by Dmitry Risovanyi
 * on 30.08.2017.
 */

public class OnSetPlaylistEvent {

    public List<Song> playlist;

    public OnSetPlaylistEvent(List<Song> playlist) {
        this.playlist = playlist;
    }

}
