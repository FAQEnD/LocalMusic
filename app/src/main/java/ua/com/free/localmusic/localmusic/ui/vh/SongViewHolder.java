package ua.com.free.localmusic.localmusic.ui.vh;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ua.com.free.localmusic.R;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = SongViewHolder.class.getSimpleName();

    public interface IViewHolderClickListener {

        void onItemClick(int pos, View v);

        void onItemLongClick(int pos, View v);

    }

    public TextView songTitle;
    public TextView songSubtitle;
    public ImageButton imageButton;

    private IViewHolderClickListener mViewHolderClickListener;

    public SongViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        songTitle = (TextView) itemView.findViewById(R.id.song_title);
        songSubtitle = (TextView) itemView.findViewById(R.id.song_subtitle);
        imageButton = (ImageButton) itemView.findViewById(R.id.song_play_pause_button);
        imageButton.setOnClickListener(this);
    }

    public void setClickListener(IViewHolderClickListener viewHolderClickListener) {
        mViewHolderClickListener = viewHolderClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mViewHolderClickListener != null) {
            mViewHolderClickListener.onItemClick(getAdapterPosition(), v);
        } else {
            Log.e(TAG, "mViewHolderClickListener in NULL");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mViewHolderClickListener != null) {
            mViewHolderClickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
        Log.e(TAG, "mViewHolderClickListener in NULL");
        return false;
    }

}
