package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.IOScheduler;
import com.giganticsheep.wifilight.di.modules.IOSchedulerModule;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by anne on 01/07/15.
 * (*_*)
 */
@IOScheduler
@Component(modules = IOSchedulerModule.class)
public interface IOSchedulerComponent {
    Scheduler ioScheduler();
}
