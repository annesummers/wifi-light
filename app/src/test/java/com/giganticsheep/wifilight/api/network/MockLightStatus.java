package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightStatus;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class MockLightStatus extends MockLightBase
                             implements LightStatus {

    private final LightControl.Status status;

    public MockLightStatus(final String id,
                           final String label,
                           final LightControl.Status status) {
        super(id, label);

        this.status = status;
    }

    @Override
    public LightControl.Status getStatus() {
        return status;
    }
}
