package ua.com.free.localmusic.networkoperations;

import android.util.Log;

import java.io.IOException;

import retrofit2.Response;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;
import ua.com.free.localmusic.networkoperations.service.NetworkService;
import ua.com.free.localmusic.networkoperations.service.NetworkServiceFactory;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public class NetworkOperation implements INetworkOperation {

    private static final String TAG = NetworkOperation.class.getSimpleName();

    private NetworkService mNetworkService;

    public NetworkOperation() {
        mNetworkService = NetworkServiceFactory.create();
    }

    @Override
    public Response<SongFromRipperServiceModel> getSongMetadata(String id) throws IOException {
        if (id == null || id.isEmpty()) {
            Log.e(TAG, "id can't be null or empty");
            return null;
        }
        Response<SongFromRipperServiceModel> response = mNetworkService.getSongMetadata("JSON", "https://www.youtube.com/watch?v=" + id).execute();
        if (response.isSuccessful() && response.body() != null) {
            return response;
        }
        Log.e(TAG, response.message());
        return response;
    }

}
