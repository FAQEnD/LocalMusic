package ua.com.free.localmusic.localmusic.controller.interfaces;

import android.content.Context;

import ua.com.free.localmusic.localmusic.ui.screen.interfaces.IMainScreen;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public interface IMainScreenController extends IScreenController<IMainScreen> {

    void onCreate(Context context);

    void askToSearchData(String query);

}