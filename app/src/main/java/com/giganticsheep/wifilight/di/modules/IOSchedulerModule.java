package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.di.IOScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 01/07/15.
 * (*_*)
 */

@Module
public class IOSchedulerModule {

    @IOScheduler
    @Provides
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }
}
