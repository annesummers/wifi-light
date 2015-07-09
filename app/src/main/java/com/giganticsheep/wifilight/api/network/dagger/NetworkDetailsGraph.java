package com.giganticsheep.wifilight.api.network.dagger;

import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.api.network.dagger.ServerURL;
import com.giganticsheep.wifilight.dagger.SchedulersGraph;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface NetworkDetailsGraph extends NetworkGraph {

    NetworkDetails networkDetails();

    @ServerURL String serverURL();
}
