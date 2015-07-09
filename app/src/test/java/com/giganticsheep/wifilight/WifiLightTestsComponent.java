package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsModule;
import com.giganticsheep.wifilight.api.network.dagger.NetworkModule;
import com.giganticsheep.wifilight.dagger.ApplicationScope;
import com.giganticsheep.wifilight.dagger.SchedulersModule;
import com.giganticsheep.wifilight.dagger.WifiLightGraph;
import com.giganticsheep.wifilight.dagger.WifiLightModule;

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
public interface WifiLightTestsComponent extends WifiLightGraph {

    final class Initializer {
        public static WifiLightTestsComponent init() {
            return DaggerWifiLightTestsComponent.builder()
                    .build();
        }

        private Initializer() {} // No instances.
    }
}