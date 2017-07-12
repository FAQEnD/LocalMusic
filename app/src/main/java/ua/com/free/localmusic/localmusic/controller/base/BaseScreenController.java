package ua.com.free.localmusic.localmusic.controller.base;

import ua.com.free.localmusic.localmusic.controller.interfaces.IScreenController;
import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IScreen;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public abstract class BaseScreenController<T extends IScreen> implements IScreenController<T> {

    protected T screen;

    public void setScreen(T screen) {
        this.screen = screen;
    }

}
