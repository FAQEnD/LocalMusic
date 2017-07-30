package ua.com.free.localmusic.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.com.free.localmusic.api.youtuberipper.YoutubeRipperAPI;
import ua.com.free.localmusic.localmusic.controller.MainScreenController;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.networkoperations.INetworkOperation;
import ua.com.free.localmusic.networkoperations.NetworkOperation;
import ua.com.free.localmusic.youtube.YoutubeAPI;

/**
 * @author anton.s.musiienko on 7/3/2017.
 */

@Module
public class MainModule {

    Application mApplication;

    public MainModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    public IMainScreenController provideMainScreenController(YoutubeAPI youtubeAPI, YoutubeRipperAPI ripperAPI) {
        return new MainScreenController(youtubeAPI, ripperAPI);
    }

    @Provides
    @Singleton
    public YoutubeAPI provideYoutubeAPI() {
        return new YoutubeAPI(mApplication);
    }

    @Provides
    @Singleton
    public INetworkOperation provideNetworkOperation() {
        return new NetworkOperation();
    }

    @Provides
    @Singleton
    public YoutubeRipperAPI provideYoutubeRipperAPI(INetworkOperation networkOperation) {
        return new YoutubeRipperAPI(networkOperation);
    }

}
