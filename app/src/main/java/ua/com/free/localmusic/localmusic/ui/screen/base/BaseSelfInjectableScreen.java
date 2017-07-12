package ua.com.free.localmusic.localmusic.ui.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ua.com.free.localmusic.LocalMusicApplication;
import ua.com.free.localmusic.di.AppComponent;

/**
 * @author anton.s.musiienko on 7/3/2017.
 */

public abstract class BaseSelfInjectableScreen extends AppCompatActivity {

    protected abstract void inject(AppComponent appComponent);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(LocalMusicApplication.getAppComponent());
    }
}
