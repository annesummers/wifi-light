package com.giganticsheep.wifilight.api.network.dagger;

import com.giganticsheep.wifilight.api.network.NetworkDetails;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface NetworkDetailsGraph {

    NetworkDetails networkDetails();

    @ServerURL String serverURL();
}
