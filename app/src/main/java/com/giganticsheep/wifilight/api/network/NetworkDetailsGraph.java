package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
interface NetworkDetailsGraph {

    @NonNull
    NetworkDetails networkDetails();

    @NonNull
    @ServerURL
    String serverURL();
}
