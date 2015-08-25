package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.network.test.TestLightNetworkModule;
import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.network.TestLightNetworkGraph;
import com.giganticsheep.wifilight.base.TestModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { TestModule.class,
                    TestLightNetworkModule.class } )
public interface TestModelComponent extends TestLightNetworkGraph {

    void inject(ModelTest wifiLightTest);
}