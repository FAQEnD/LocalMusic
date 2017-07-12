package ua.com.free.localmusic.localmusic.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.services.youtube.model.SearchResult;

import java.util.List;

import ua.com.free.localmusic.R;
import ua.com.free.localmusic.localmusic.ui.vh.SongViewHolder;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private List<SearchResult> mSongs;
    private SongViewHolder.IViewHolderClickListener mClickListener;

    public SongAdapter(List<SearchResult> songs, SongViewHolder.IViewHolderClickListener clickListener) {
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
        holder.mSongTitle.setText(mSongs.get(position).getSnippet().getTitle());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void setData(List<SearchResult> songs) {
        mSongs = songs;
        notifyDataSetChanged();
    }

}
