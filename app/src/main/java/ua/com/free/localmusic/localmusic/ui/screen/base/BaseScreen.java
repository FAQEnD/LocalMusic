package ua.com.free.localmusic.localmusic.ui.screen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public abstract class BaseScreen extends BaseSelfInjectableScreen {

    protected abstract void setScreen();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreen();
    }
}
