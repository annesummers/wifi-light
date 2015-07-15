package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightStatus;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class TestLightStatus implements LightStatus {

    public TestLightStatus(final String id,
                           final String name,
                           final LightControl.Status status) {
    }

    @Override
    public LightControl.Status getStatus() {
        return null;
    }

    @Override
    public String id() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
