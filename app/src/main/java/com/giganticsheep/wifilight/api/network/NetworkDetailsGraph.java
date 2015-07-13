package com.giganticsheep.wifilight.api.network;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
interface NetworkDetailsGraph {

    NetworkDetails networkDetails();

    @ServerURL
    String serverURL();
}
