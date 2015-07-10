package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsGraph;
import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsModule;
import com.giganticsheep.wifilight.api.network.dagger.NetworkGraph;
import com.giganticsheep.wifilight.api.network.dagger.NetworkModule;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = {  WifiLightAppModule.class,
                        WifiLightModule.class,
                        SchedulersModule.class,
                        NetworkDetailsModule.class,
                        NetworkModule.class} )
public interface WifiLightAppComponent extends WifiLightAppGraph,
                                                WifiLightGraph,
                                                SchedulersGraph,
                                                NetworkDetailsGraph,
                                                NetworkGraph {

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
