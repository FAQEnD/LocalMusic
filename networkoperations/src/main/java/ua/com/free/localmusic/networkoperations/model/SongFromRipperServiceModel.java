package ua.com.free.localmusic.networkoperations.model;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public class SongFromRipperServiceModel {

    String title;
    String link;
    String length;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "title: " + title + " link: " + link + " length: " + length;
    }
}
