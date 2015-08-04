package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.GroupData;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class MockLightBase implements GroupData {

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
    public String getId() {
        return id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }
}
