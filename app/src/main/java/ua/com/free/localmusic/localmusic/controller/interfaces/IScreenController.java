package ua.com.free.localmusic.localmusic.controller.interfaces;

import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IScreen;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public interface IScreenController <T extends IScreen> {

    void setScreen(T screen);

}
