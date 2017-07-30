package ua.com.free.localmusic.localmusic.manager.impl;

import android.util.Log;

import java.util.List;

import ua.com.free.localmusic.localmusic.manager.IPlaylist;
import ua.com.free.localmusic.models.Song;

/**
 * @author Anton Musiienko on 7/30/2017.
 */
public class Playlist implements IPlaylist {

    private static final String TAG = "Playlist";

    private int mId;
    private List<Song> mPlaylist;
    private int mSongIndex;

    public Playlist(List<Song> playlist) {
        mId = playlist.size() + playlist.get(0).getId().hashCode();
        mPlaylist = playlist;
        mSongIndex = -1;
        Log.d(TAG, "playlist with ID: " + mId + " was created");
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

    @Override
    public int getCurrentPosition() {
        return mSongIndex;
    }

    @Override
    public int getId() {
        return mId;
    }
}
