package com.giganticsheep.wifilight.ui.locations;

import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.ui.base.ViewBase;

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
    void showLightNetwork(LightNetwork lightNetwork,
                          int locationPosition);
}
