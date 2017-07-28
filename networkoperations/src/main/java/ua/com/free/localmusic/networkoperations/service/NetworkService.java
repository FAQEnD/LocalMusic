package ua.com.free.localmusic.networkoperations.service;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public interface NetworkService {

    @GET("/fetch")
    Call<SongFromRipperServiceModel> getSongMetadata(@Query("format") String format, @Query("video") String id) throws IOException;

}
