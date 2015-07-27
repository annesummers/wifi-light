package com.giganticsheep.wifilight.ui.base;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.control.LightNetwork;
import com.giganticsheep.wifilight.ui.control.LightNetworkPresenterTest;
import com.giganticsheep.wifilight.ui.control.LightNetworkView;

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
    private int childPosition;
    private int groupPosition;

    private int state;

    public TestLightNetworkView(@NonNull final LightNetworkPresenterTest lightNetworkPresenterTest) {
        this.lightNetworkPresenterTest = lightNetworkPresenterTest;
    }

    @Override
    public void showLightNetwork(LightNetwork lightNetwork,
                                 int groupPosition,
                                 int childPosition) {
        state = STATE_SHOW_LIGHT_NETWORK;

        this.lightNetwork = lightNetwork;
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
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

    public int getChildPosition() {
        return childPosition;
    }

    public int getGroupPosition() {
        return groupPosition;
    }
}
