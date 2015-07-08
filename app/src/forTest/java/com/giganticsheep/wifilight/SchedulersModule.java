package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.di.ApplicationScope;
import com.giganticsheep.wifilight.di.IOScheduler;
import com.giganticsheep.wifilight.di.UIScheduler;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module
public class SchedulersModule {

    @Provides
    @UIScheduler
    @ApplicationScope
    Scheduler provideUIScheduler() {
        return Schedulers.immediate();
    }

    @Provides
    @IOScheduler
    @ApplicationScope
    Scheduler provideIOScheduler() {
        return Schedulers.immediate();
    }
}
