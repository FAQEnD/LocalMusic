package ua.com.free.localmusic.localmusic.manager.impl;

import java.util.List;

import ua.com.free.localmusic.localmusic.manager.IPlaylistManager;
import ua.com.free.localmusic.models.Song;

/**
 * @author Anton Musiienko on 7/30/2017.
 */
public class PlaylistManager implements IPlaylistManager {

    private List<Song> mPlaylist;
    private int mSongIndex;

    public PlaylistManager(List<Song> playlist) {
        mPlaylist = playlist;
        mSongIndex = 0;
    }

    @Override
    public Song getCurrent() {
        if (mSongIndex < 0 || mSongIndex > mPlaylist.size()) {
            return null;
        }
        return mPlaylist.get(mSongIndex);
    }

    @Override
    public Song getNext() {
        if (mSongIndex >= mPlaylist.size()) {
            return null;
        }
        return mPlaylist.get(mSongIndex + 1);
    }

    @Override
    public Song getPrevious() {
        if (mSongIndex <= 0) {
            return null;
        }
        return mPlaylist.get(mSongIndex - 1);
    }

    @Override
    public void onNext() {
        mSongIndex++;
    }

    @Override
    public void onPrevious() {
        mSongIndex--;
    }

    @Override
    public void seekTo(int position) {
        mSongIndex = position;
    }
}
