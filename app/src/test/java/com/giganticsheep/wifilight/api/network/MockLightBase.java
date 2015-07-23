package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.WifiLightData;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class MockLightBase implements WifiLightData {

    @NonNull
    public final String id;

    @NonNull
    public final String label;

    public MockLightBase(@NonNull final String id,
                         @NonNull final String label) {
        this.id = id;
        this.label = label;
    }

    @NonNull
    @Override
    public String id() {
        return id;
    }

    @NonNull
    @Override
    public String getLabel() {
        return label;
    }
}
