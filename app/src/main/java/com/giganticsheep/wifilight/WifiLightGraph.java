package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.base.BaseApplication;

import rx.Scheduler;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface WifiLightGraph {
    void inject(WifiLightApplication application);

    WifiLightApplication application();
    BaseLogger baseLogger();
    EventBus eventBus();
    BaseApplication.FragmentFactory fragmentFactory();

    LightService lightService();

    NetworkDetails networkDetails();
}
