package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.LightControl;

/**
 * DESCRIPTION HERE ANNE <p>
 * <p/>
 * Created by anne on 15/07/15. <p>
 * <p/>
 * (*_*)
 */
public interface LightStatus extends WifiLightData {
    LightControl.Status getStatus();
}
