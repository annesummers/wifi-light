package com.giganticsheep.wifilight.base.dagger;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;

import timber.log.Timber;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface WifiLightGraph {

    EventBus eventBus();
    FragmentFactory fragmentFactory();
    Timber.Tree tree();
}
