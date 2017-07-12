package ua.com.free.localmusic.di.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.com.free.localmusic.api.youtube.YoutubeAPI;
import ua.com.free.localmusic.localmusic.controller.MainScreenController;
import ua.com.free.localmusic.localmusic.controller.interfaces.IMainScreenController;

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
    public IMainScreenController provideMainScreenController(YoutubeAPI youtubeAPI) {
        return new MainScreenController(youtubeAPI);
    }

    @Provides
    @Singleton
    public YoutubeAPI provideYoutubeAPI() {
        return new YoutubeAPI(mApplication);
    }

}
