package com.giganticsheep.wifilight.ui;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.api.network.TestLightNetworkGraph;
import com.giganticsheep.wifilight.api.network.test.TestLightNetworkModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.control.network.LightNetworkAdapterBase;

import dagger.Component;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { TestUIModule.class,
                    TestLightNetworkModule.class} )
public interface TestUIComponent extends TestLightNetworkGraph,
                                        LightNetworkAdapterBase.Injector {

    void inject(UITestBase UITest);
}
