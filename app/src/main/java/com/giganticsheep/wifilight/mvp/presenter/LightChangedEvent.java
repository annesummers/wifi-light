package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.api.model.Light;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class LightChangedEvent {
    private final Light light;

    public LightChangedEvent(Light light) {
        this.light = light;
    }

    public final Light getLight() {
        return light;
    }
}
