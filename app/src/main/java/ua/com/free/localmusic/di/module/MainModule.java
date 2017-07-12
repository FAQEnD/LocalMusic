package ua.com.free.localmusic.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.com.free.localmusic.controller.MainScreenController;
import ua.com.free.localmusic.controller.interfaces.IMainScreenController;
import ua.com.free.localmusic.ui.screen.interfaces.IMainScreen;

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
    public IMainScreenController provideMainScreenController() {
        return new MainScreenController();
    }

}
