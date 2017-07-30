package ua.com.free.localmusic.api.youtuberipper;

import io.reactivex.Observable;
import retrofit2.Response;
import ua.com.free.localmusic.networkoperations.INetworkOperation;
import ua.com.free.localmusic.networkoperations.model.SongFromRipperServiceModel;

/**
 * @author anton.s.musiienko on 7/28/2017.
 */
public class YoutubeRipperAPI {

    private static final String TAG = YoutubeRipperAPI.class.getSimpleName();

    private INetworkOperation mNetworkOperation;

    public YoutubeRipperAPI(INetworkOperation networkOperation) {
        mNetworkOperation = networkOperation;
    }

    /**
     * Get download mp3 download link by video ID
     *
     * @param id - for a specific video
     * @return link, title and length of specific mp3
     */
    public Observable<SongFromRipperServiceModel> getSongMetadata(String id) {
        return Observable.create(e -> {
            try {
                Response<SongFromRipperServiceModel> response = mNetworkOperation.getSongMetadata(id);
                if (response.isSuccessful() && response.body() != null) {
                    e.onNext(response.body());
                } else {
                    e.onError(new IllegalStateException(response.message()));
                }
                e.onComplete();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
