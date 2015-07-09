package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.api.network.dagger.NetworkDetailsGraph;

import rx.Scheduler;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface SchedulersGraph extends NetworkDetailsGraph {

    @IOScheduler
    Scheduler iOScheduler();
    @UIScheduler
    Scheduler uIScheduler();
}
