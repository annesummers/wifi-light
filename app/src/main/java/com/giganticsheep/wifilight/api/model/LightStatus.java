package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.LightControl;

/**
 * The status of a {@link com.giganticsheep.wifilight.api.model.Light}. <p>
 *
 * Created by anne on 15/07/15. <p>
 *
 * (*_*)
 */
public interface LightStatus {

    String getId();

    LightControl.Status getStatus();
}
