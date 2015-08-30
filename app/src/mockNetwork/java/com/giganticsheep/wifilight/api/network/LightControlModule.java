package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.SchedulersModule;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;
import com.giganticsheep.wifilight.base.error.ErrorStrings;

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

    @NonNull
    @Provides
    @ApplicationScope
    LightControl provideLightControl(@NonNull NetworkDetails networkDetails,
                                     @NonNull LightService lightService,
                                     @NonNull EventBus eventBus,
                                     @NonNull ErrorStrings errorStrings,
                                     @IOScheduler Scheduler ioScheduler,
                                     @UIScheduler Scheduler uiScheduler) {
        return new LightControlImpl(networkDetails,
                lightService,
                eventBus,
                errorStrings,
                ioScheduler,
                uiScheduler);
    }
}
