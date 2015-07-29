package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.network.LightControlGraph;
import com.giganticsheep.wifilight.api.network.LightControlModule;
import com.giganticsheep.wifilight.api.network.TestLightNetworkGraph;
import com.giganticsheep.wifilight.api.network.TestLightNetworkModule;
import com.giganticsheep.wifilight.base.dagger.SchedulersGraph;
import com.giganticsheep.wifilight.base.dagger.WifiLightGraph;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = { LightControlModule.class,
                        TestLightNetworkModule.class } )
public interface WifiLightTestsComponent extends WifiLightGraph,
                                                SchedulersGraph,
                                                LightControlGraph,
                                                TestLightNetworkGraph {

    final class Initializer {
        public static WifiLightTestsComponent init() {
            return DaggerWifiLightTestsComponent.builder()
                    .build();
        }

        private Initializer() {} // No instances.
    }
}