package ua.com.free.localmusic.events;

import java.util.List;

import ua.com.free.localmusic.models.Song;

/**
 * Created by Dmitry Risovanyi
 * on 30.08.2017.
 */

public class OnSetPlaylistAndPlayEvent {

    public List<Song> playlist;

    public int position;

    public OnSetPlaylistAndPlayEvent(List<Song> playlist, int position) {
        this.playlist = playlist;
        this.position = position;
    }
}
