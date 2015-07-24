package com.giganticsheep.wifilight.mvp.view;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterTestBase;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class TestLightView implements LightView {

    public static final int STATE_SHOW_LOADING = 0;
    public static final int STATE_SHOW_LIGHT_CONNECTED = 1;
    public static final int STATE_SHOW_LIGHT_CONNECTING = 2;
    public static final int STATE_SHOW_LIGHT_DISCONNECTED = 3;
    public static final int STATE_SHOW_ERROR = 4;

    private LightPresenterTestBase lightPresenterTestBase;
    private int state = STATE_SHOW_LOADING;

    public TestLightView(@NonNull final LightPresenterTestBase lightPresenterTestBase, 
                         @NonNull final BaseLogger baseLogger) {
        this.lightPresenterTestBase = lightPresenterTestBase;
    }

    @Override
    public void showLoading() {
        state = STATE_SHOW_LOADING;

        Timber.w("showLoading()");
    }

    @Override
    public void showError() {
        state = STATE_SHOW_ERROR;

        Timber.w("showError()");
    }

    @Override
    public void showError(@NonNull Throwable throwable) {
        state = STATE_SHOW_ERROR;

        Timber.w("showError() " + throwable.getMessage());
    }

    @Override
    public void showConnected() {
        state = STATE_SHOW_LIGHT_CONNECTED;

        Timber.w("showConnected()");
    }

    @Override
    public void showConnecting() {
        state = STATE_SHOW_LIGHT_CONNECTING;

        Timber.w("showConnecting()");
    }

    @Override
    public void showDisconnected() {
        state = STATE_SHOW_LIGHT_DISCONNECTED;

        Timber.w("showDisconnected()");
    }

    public int getState() {
        return state;
    }
}
