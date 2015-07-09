package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface WifiLightGraph extends SchedulersGraph {

    BaseLogger baseLogger();
    EventBus eventBus();
    FragmentFactory fragmentFactory();
}
