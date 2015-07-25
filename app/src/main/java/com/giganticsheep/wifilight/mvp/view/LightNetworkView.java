package com.giganticsheep.wifilight.mvp.view;

import com.giganticsheep.wifilight.mvp.presenter.LightNetworkPresenter;

/**
 * Interface implemented by the DrawerAdapter for the Presenter to control the UI.
 * <p>
 * Created by anne on 24/07/15.
 * <p>
 * (*_*)
 */
public interface LightNetworkView extends ViewBase {

    /**
     * Show the getLight network view.
     */
    void showLightNetwork(LightNetworkPresenter.LightNetwork lightNetwork);
}
