package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.ui.control.LightNetwork;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 29/07/15. <p>
 * (*_*)
 */
public class FetchLightNetworkEvent {
    private LightNetwork lightNetwork;

    public FetchLightNetworkEvent(LightNetwork lightNetwork) {
        this.lightNetwork = lightNetwork;
    }

    public LightNetwork lightNetwork() {
        return lightNetwork;
    }
}
