package com.giganticsheep.wifilight.dagger;

import rx.Scheduler;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface SchedulersGraph {

    @IOScheduler Scheduler iOScheduler();
    @UIScheduler Scheduler uIScheduler();
}
