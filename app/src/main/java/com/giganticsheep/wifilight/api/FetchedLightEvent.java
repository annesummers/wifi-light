package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.api.model.Light;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class FetchedLightEvent {
    private final Light light;

    public FetchedLightEvent(Light light) {
        this.light = light;
    }

    public final Light getLight() {
        return light;
    }

    @Override
    public String toString() {
        return "FetchedLightEvent{" +
                "getLight=" + light +
                '}';
    }
}
