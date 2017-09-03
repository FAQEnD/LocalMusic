package ua.com.free.localmusic.events;

/**
 * Created by Dmitry Risovanyi
 * on 30.08.2017.
 */

public class OnPlaySongEvent {

    public int position;

    public OnPlaySongEvent(int position) {
        this.position = position;
    }
}
