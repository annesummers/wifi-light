package com.giganticsheep.wifilight.dagger;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
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
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @IOScheduler
    @ApplicationScope
    Scheduler provideIOScheduler() {
        return Schedulers.io();
    }
}
