package ua.com.free.localmusic.controller.base;
/*
 * Copyright (C) 2017 Funambol.
 * All Rights Reserved.  No use, copying or distribution of this
 * work may be made except in accordance with a valid license
 * agreement from Funambol.  This notice must be
 * included on all copies, modifications and derivatives of this
 * work.
 *
 * Funambol MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. Funambol SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */

import ua.com.free.localmusic.controller.interfaces.IScreenController;
import ua.com.free.localmusic.ui.screen.interfaces.IScreen;

/**
 * @author anton.s.musiienko on 7/12/2017.
 */

public abstract class BaseScreenController<T extends IScreen> implements IScreenController<T> {

    protected T screen;

    public void setScreen(T screen) {
        this.screen = screen;
    }

}
