package ua.com.free.localmusic;

import android.app.Application;

import ua.com.free.localmusic.di.AppComponent;
import ua.com.free.localmusic.di.DaggerAppComponent;
import ua.com.free.localmusic.di.module.MainModule;

/**
 * @author anton.s.musiienko on 7/3/2017.
 */

public class LocalMusicApplication extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = DaggerAppComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

}
