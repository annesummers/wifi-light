package com.giganticsheep.wifilight.ui.base;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class LightChangedEvent {
    private final String lightId;

    public LightChangedEvent(String lightId) {
        this.lightId = lightId;
    }

    public final String getLightId() {
        return lightId;
    }
}
