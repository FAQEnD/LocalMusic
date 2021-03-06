package ua.com.free.localmusic.localmusic.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.com.free.localmusic.R;
import ua.com.free.localmusic.localmusic.ui.vh.SongViewHolder;
import ua.com.free.localmusic.models.Song;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private static final String TITLE_SPLITTER = "-";
    private List<Song> mSongs;
    private SongViewHolder.IViewHolderClickListener mClickListener;

    public SongAdapter(List<Song> songs, SongViewHolder.IViewHolderClickListener clickListener) {
        mSongs = songs;
        mClickListener = clickListener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_song_item, parent, false);
        SongViewHolder songViewHolder = new SongViewHolder(rootView);
        songViewHolder.setClickListener(mClickListener);
        return songViewHolder;
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = mSongs.get(position);
        String title = song.getTitle();
        if (title.contains(TITLE_SPLITTER)) {
            String[] titleAndSubtitle = title.split(TITLE_SPLITTER);
            holder.songTitle.setText(titleAndSubtitle[0].replaceAll(" ", ""));
            holder.songSubtitle.setText(titleAndSubtitle[1].replaceAll(" ", ""));
        } else {
            holder.songTitle.setText(title);
            holder.songSubtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void setData(List<Song> songs) {
        mSongs = songs;
        notifyDataSetChanged();
    }

    public List<Song> getData() {
        return mSongs;
    }

}
