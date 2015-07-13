package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.dagger.LightControlGraph;
import com.giganticsheep.wifilight.api.dagger.LightControlModule;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = {  WifiLightAppModule.class,
                        WifiLightModule.class,
                        SchedulersModule.class,
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
