package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.base.dagger.SchedulersModule;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;

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
        return new LightControlImpl(networkDetails,
                lightService,
                eventBus,
                baseLogger,
                ioScheduler,
                uiScheduler);
    }
}
