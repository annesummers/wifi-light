package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.di.ApplicationScope;
import com.giganticsheep.wifilight.api.network.NetworkModule;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@ApplicationScope
@Component(modules = { WifiLightAppModule.class,
                        SchedulersModule.class,
                        NetworkModule.class} )
public interface WifiLightAppComponent extends SchedulersGraph {

    final class Initializer {
        static WifiLightAppComponent init(WifiLightApplication app) {
            return DaggerWifiLightAppComponent.builder()
                    .wifiLightAppModule(new WifiLightAppModule(app))
                    .build();
        }

        private Initializer() {} // No instances.
    }
}
