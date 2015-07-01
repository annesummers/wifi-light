package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.UIScheduler;
import com.giganticsheep.wifilight.di.modules.IOSchedulerModule;
import com.giganticsheep.wifilight.di.modules.MainThreadSchedulerModule;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by anne on 01/07/15.
 * (*_*)
 */
@UIScheduler
@Component(modules = MainThreadSchedulerModule.class)
public interface MainThreadSchedulerComponent {
    Scheduler mainThreadScheduler();
}
