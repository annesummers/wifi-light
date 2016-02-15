package com.giganticsheep.wifilight.ui.base.light;

import com.giganticsheep.wifilight.api.model.Light;

/**
 * Interface implemented by Light UI elements for the Presenters to control the UI.
 * <p>
 * Created by anne on 29/06/15.
 * <p>
 * (*_*)
 */
public interface LightView extends ViewBase {

    /**
     * Show the connected view.
     * @param light
     */
    void showConnected(Light light);

    /**
     * Show the connecting view.
     * @param light
     */
    void showConnecting(Light light);

    /**
     * Show the disconnected view.
     * @param light
     */
    void showDisconnected(Light light);
}
