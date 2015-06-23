package com.giganticsheep.wifilight.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class LightGroup extends WifiLightObject {

    private final LightsList mLights = new LightsList();

    /**
     * @param network the network this object is part of
     * @param id      the id of this object
     */
    protected LightGroup(LightNetwork network, String id) {
        super(network, id);
    }

    void addLightToGroup(Light light) {
        mLights.add(light);
    }

    void removeLightFromGroup(Light light) {
        mLights.remove(light);
    }
}
