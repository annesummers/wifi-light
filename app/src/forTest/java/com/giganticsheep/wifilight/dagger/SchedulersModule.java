package com.giganticsheep.wifilight.dagger;

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
