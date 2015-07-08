package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkModule;
import com.giganticsheep.wifilight.di.ApplicationScope;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = { WifiLightAppModule.class,
                        SchedulersModule.class,
                        NetworkDetailsModule.class,
                        NetworkModule.class} )
public interface WifiLightAppComponent extends NetworkDetailsGraph {

    final class Initializer {
        static WifiLightAppComponent init(WifiLightApplication app) {
            return DaggerWifiLightAppComponent.builder()
                    .wifiLightAppModule(new WifiLightAppModule(app))
                    .build();
        }

        private Initializer() {} // No instances.
    }
}
