package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetailsGraph;
import com.giganticsheep.wifilight.api.network.NetworkDetailsModule;
import com.giganticsheep.wifilight.api.network.NetworkModule;
import com.giganticsheep.wifilight.di.ApplicationScope;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
@ApplicationScope
@Component(modules = { WifiLightModule.class,
                    SchedulersModule.class,
                    NetworkDetailsModule.class,
                    NetworkModule.class} )
public interface WifiLightTestComponent extends NetworkDetailsGraph {

    void inject(WifiLightTest wifiLightTest);

   /* final class Initializer {
        static WifiLightTestComponent init() {
            return DaggerWifiLightTestComponent.builder()
                    .wifiLightModule(new WifiLightModule())
                    .build();
        }

        private Initializer() {} // No instances.
    }*/
}