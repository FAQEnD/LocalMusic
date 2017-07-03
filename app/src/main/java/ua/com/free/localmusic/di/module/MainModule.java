package ua.com.free.localmusic.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    public SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

}
