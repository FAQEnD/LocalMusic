package ua.com.free.localmusic.networkoperations;

import java.io.IOException;

import retrofit2.Response;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public interface INetworkOperation {

    Response<SongFromRipperServiceModel> getSongMetadata(String id) throws IOException;

}
