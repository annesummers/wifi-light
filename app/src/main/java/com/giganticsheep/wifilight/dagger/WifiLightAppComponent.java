package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsModule;
import com.giganticsheep.wifilight.api.network.dagger.NetworkModule;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = { WifiLightModule.class,
                        WifiLightAppModule.class,
                        SchedulersModule.class,
                        NetworkDetailsModule.class,
                        NetworkModule.class} )
public interface WifiLightAppComponent extends WifiLightAppGraph {

    final class Initializer {
        public static WifiLightAppComponent init(WifiLightApplication app) {
            return DaggerWifiLightAppComponent.builder()
                    .wifiLightAppModule(new WifiLightAppModule(app))
                    .build();
        }

        private Initializer() {} // No instances.
    }
}
