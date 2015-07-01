package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.di.UIScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 01/07/15.
 * (*_*)
 */

@Module
public class MainThreadSchedulerModule {

    @UIScheduler
    @Provides
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
