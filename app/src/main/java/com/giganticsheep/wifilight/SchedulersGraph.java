package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.di.IOScheduler;
import com.giganticsheep.wifilight.di.UIScheduler;

import rx.Scheduler;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */
public interface SchedulersGraph extends WifiLightGraph {

    @IOScheduler
    Scheduler iOScheduler();
    @UIScheduler
    Scheduler uIScheduler();
}
