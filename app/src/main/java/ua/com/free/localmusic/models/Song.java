package ua.com.free.localmusic.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */
public class Song implements Parcelable {

    private String title;
    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.id);
    }

    public Song() {
    }

    protected Song(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
