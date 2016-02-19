package com.giganticsheep.nofragmentbase.ui.base;

import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by anne on 16/02/16.
 */
public class ContainerDelegate<S extends Screen> {

    private final ViewState viewState;
    private final Screen.ViewActionBase viewAction;
    private final ViewGroup viewGroup;

    private boolean isOnScreen = false;
    private S screen;

    ContainerDelegate(ViewState viewState,
                             Screen.ViewActionBase viewAction,
                             ViewGroup view) {
        this.viewState = viewState;
        this.viewAction = viewAction;
        this.viewGroup = view;
    }

    ContainerDelegate(Screen.ViewActionBase viewAction,
                      ViewGroup view) {
        this.viewState = null;
        this.viewAction = viewAction;
        this.viewGroup = view;
    }

    ContainerDelegate(ViewGroup view) {
        this.viewState = null;
        this.viewAction = null;
        this.viewGroup = view;
    }

    void bindView() {
        ButterKnife.inject(viewGroup, viewGroup);
        // ButterKnife.bind(this, this);
    }

    float getXFraction() {
        final int width = viewGroup.getWidth();
        if (width != 0) return viewGroup.getX() / viewGroup.getWidth();
        else return viewGroup.getX();
    }

    void setXFraction(float xFraction) {
        final int width = viewGroup.getWidth();
        viewGroup.setX((width > 0) ? (xFraction * width) : -9999);
    }

    void onAttachedToWindow() {
        if (viewAction != null) {
            if(viewState != null) {
                screen.attachView(viewState, viewAction);
            } else {
                screen.attachView(viewAction);
            }
        }

        isOnScreen = true;
    }

    void onDetachedFromWindow() {
        if (viewAction != null) {
            if(viewState != null) {
                screen.detachView(viewState, viewAction);
            } else {
                screen.detachView(viewAction);
            }
        }

        isOnScreen = false;
    }

    void setScreen(S screen) {
        this.screen = screen;
    }

    ScreenGroup getScreenGroup() {
        return screen.getScreenGroup();
    }

    boolean isOnScreen() {
        return isOnScreen;
    }

    public S getScreen() {
        return screen;
    }
}
