package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.ServerURL;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface NetworkDetailsGraph extends SchedulersGraph {

    NetworkDetails networkDetails();

    @ServerURL String serverURL();
}
