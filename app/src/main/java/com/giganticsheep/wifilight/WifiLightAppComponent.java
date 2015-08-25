package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.LightControlGraph;
import com.giganticsheep.wifilight.api.network.LightControlModule;
import com.giganticsheep.wifilight.base.dagger.SchedulersGraph;
import com.giganticsheep.wifilight.base.dagger.WifiLightGraph;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
@ApplicationScope
@Component(modules = {  WifiLightAppModule.class,
                        WifiLightModule.class,
                        LightControlModule.class } )
public interface WifiLightAppComponent extends WifiLightAppGraph,
                                                WifiLightGraph,
                                                SchedulersGraph,
                                                LightControlGraph {

    final class Initializer {
        public static WifiLightAppComponent init(WifiLightApplication app) {
            return DaggerWifiLightAppComponent.builder()
                    .wifiLightModule(new WifiLightModule(app))
                    .wifiLightAppModule(new WifiLightAppModule(app))
                    .build();
        }

        private Initializer() {} // No instances.
    }
}
