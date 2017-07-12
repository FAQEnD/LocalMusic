package ua.com.free.localmusic.utils;

import com.google.api.services.youtube.model.SearchResult;

import ua.com.free.localmusic.models.Song;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */
public class ConvertUtils {

    public static Song convertToSong(SearchResult searchResult) {
        Song song = new Song();
        song.setId(searchResult.getId().getVideoId());
        song.setTitle(searchResult.getSnippet().getTitle());
        return song;
    }

}
