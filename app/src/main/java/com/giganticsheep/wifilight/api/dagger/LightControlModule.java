package com.giganticsheep.wifilight.api.dagger;

import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsModule;
import com.giganticsheep.wifilight.dagger.WifiLightModule;
import com.giganticsheep.wifilight.dagger.SchedulersModule;

import com.giganticsheep.wifilight.api.network.dagger.NetworkModule;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.dagger.ApplicationScope;
import com.giganticsheep.wifilight.dagger.IOScheduler;
import com.giganticsheep.wifilight.dagger.UIScheduler;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */

@Module( includes = {   NetworkModule.class,
                        NetworkDetailsModule.class,
                        WifiLightModule.class,
                        SchedulersModule.class }  )
public class LightControlModule {

    @Provides
    @ApplicationScope
    LightControl provideLightControl(NetworkDetails networkDetails,
                                     LightService lightService,
                                     EventBus eventBus,
                                     BaseLogger baseLogger,
                                     @IOScheduler Scheduler ioScheduler,
                                     @UIScheduler Scheduler uiScheduler) {
        return new LightNetwork(networkDetails,
                lightService,
                eventBus,
                baseLogger,
                ioScheduler,
                uiScheduler);
    }
}
