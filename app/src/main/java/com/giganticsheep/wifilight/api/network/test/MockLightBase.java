package com.giganticsheep.wifilight.api.network.test;

import android.support.annotation.NonNull;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
public class MockLightBase {

    @NonNull
    public String id;

    @NonNull
    public String label;

    public MockLightBase(@NonNull final String id,
                         @NonNull final String label) {
        this.id = id;
        this.label = label;
    }

    public MockLightBase() { }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }
}
