package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.LightControlGraph;
import com.giganticsheep.wifilight.api.network.LightControlModule;
import com.giganticsheep.wifilight.base.dagger.SchedulersGraph;
import com.giganticsheep.wifilight.base.dagger.SchedulersModule;
import com.giganticsheep.wifilight.base.dagger.WifiLightGraph;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = { WifiLightModule.class,
                        SchedulersModule.class,
                        LightControlModule.class} )
public interface WifiLightTestsComponent extends WifiLightGraph,
                                                SchedulersGraph,
                                                LightControlGraph {

    final class Initializer {
        public static WifiLightTestsComponent init() {
            return DaggerWifiLightTestsComponent.builder()
                    .build();
        }

        private Initializer() {} // No instances.
    }
}