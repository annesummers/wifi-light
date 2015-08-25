package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.test.TestLightNetworkModule;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.SchedulersModule;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;
import com.giganticsheep.wifilight.ui.control.LightNetwork;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */

@Module( includes = {   WifiLightModule.class,
                        SchedulersModule.class,
                        TestLightNetworkModule.class,
                        NetworkModule.class }  )
public class LightControlModule {

    @NonNull
    @Provides
    @ApplicationScope
    LightControl provideLightControl(@NonNull EventBus eventBus,
                                     @IOScheduler Scheduler ioScheduler,
                                     @UIScheduler Scheduler uiScheduler,
                                     @NonNull LightNetwork lightNetwork) {
        return new MockLightControlImpl(
                eventBus,
                ioScheduler,
                uiScheduler,
                lightNetwork);
    }
}
