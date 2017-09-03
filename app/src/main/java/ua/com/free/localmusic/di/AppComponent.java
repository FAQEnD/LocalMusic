package ua.com.free.localmusic.di;

import javax.inject.Singleton;

import dagger.Component;
import ua.com.free.localmusic.di.module.MainModule;
import ua.com.free.localmusic.localmusic.manager.impl.MediaPlayerService;
import ua.com.free.localmusic.localmusic.ui.screen.MainScreen;

/**
 * @author anton.s.musiienko on 7/3/2017.
 */


@Singleton
@Component(modules = {MainModule.class})
public interface AppComponent {

    void inject(MainScreen screen);

    void inject(MediaPlayerService service);

}
