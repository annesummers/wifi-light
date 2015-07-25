package com.giganticsheep.wifilight.mvp.view;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterTestBase;

import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class TestLightView implements LightView {

    public static final int STATE_SHOW_LOADING = 0;
    public static final int STATE_SHOW_ERROR = 1;
    public static final int STATE_SHOW_LIGHT_CONNECTED = 2;
    public static final int STATE_SHOW_LIGHT_CONNECTING = 3;
    public static final int STATE_SHOW_LIGHT_DISCONNECTED = 4;

    private LightPresenterTestBase lightPresenterTestBase;
    private int state = STATE_SHOW_LOADING;
    private Light light;

    public TestLightView(@NonNull final LightPresenterTestBase lightPresenterTestBase) {
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
    public void showConnected(Light light) {
        state = STATE_SHOW_LIGHT_CONNECTED;
        this.light = light;

        Timber.w("showConnected()");
    }

    @Override
    public void showConnecting(Light light) {
        state = STATE_SHOW_LIGHT_CONNECTING;
        this.light = light;

        Timber.w("showConnecting()");
    }

    @Override
    public void showDisconnected(Light light) {
        state = STATE_SHOW_LIGHT_DISCONNECTED;
        this.light = light;

        Timber.w("showDisconnected()");
    }

    public int getState() {
        return state;
    }

    public Light getLight() {
        return light;
    }
}
