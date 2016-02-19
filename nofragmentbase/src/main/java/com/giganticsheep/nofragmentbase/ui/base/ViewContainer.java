package com.giganticsheep.nofragmentbase.ui.base;

/**
 * Created by anne on 03/11/15.
 */
public interface ViewContainer<S extends Screen> {

    void setScreen(S screen);
    S getScreen();

    float getXFraction();
    void setXFraction(float x);

    ScreenGroup getScreenGroup();

    boolean isOnScreen();
}
