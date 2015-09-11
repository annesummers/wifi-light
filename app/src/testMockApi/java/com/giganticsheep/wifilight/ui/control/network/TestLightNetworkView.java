package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.ui.locations.LightNetworkView;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 27/07/15. <p>
 * (*_*)
 */
public class TestLightNetworkView implements LightNetworkView {

    public static final int STATE_SHOW_LOADING = 0;
    public static final int STATE_SHOW_ERROR = 1;
    public static final int STATE_SHOW_LIGHT_NETWORK = 2;

    private final LightNetworkPresenterTest lightNetworkPresenterTest;

    private LightNetwork lightNetwork;
    private int locationPosition;

    private int state;

    public TestLightNetworkView(@NonNull final LightNetworkPresenterTest lightNetworkPresenterTest) {
        this.lightNetworkPresenterTest = lightNetworkPresenterTest;
    }

    @Override
    public void showLightNetwork(final LightNetwork lightNetwork,
                                 final int locationPosition) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.locationPosition = locationPosition;
    }

    @Override
    public void showLoading() {
        state = STATE_SHOW_LOADING;
    }

    @Override
    public void showError() {
        state = STATE_SHOW_ERROR;
    }

    @Override
    public void showError(Throwable throwable) {
        state = STATE_SHOW_ERROR;
    }

    public int getState() {
        return state;
    }

    public LightNetwork getLightNetwork() {
        return lightNetwork;
    }

    public int getLocationPosition() {
        return locationPosition;
    }
}
