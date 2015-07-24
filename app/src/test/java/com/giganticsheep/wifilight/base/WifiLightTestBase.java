package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.api.LightControl;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public abstract class WifiLightTestBase {

    @Inject protected LightControl lightNetwork;
    @Inject protected EventBus eventBus;
    @Inject protected FragmentFactory fragmentFactory;

    private boolean signal = false;

    protected WifiLightTestBase() {
        createComponentAndInjectDependencies();
    }

    public synchronized void signal() {
        Timber.i(Thread.currentThread().getId() + " signal");
        signal = true;
    }

    public synchronized boolean isSignalled() {
        if(signal) {
            Timber.i(Thread.currentThread().getId() + " signal == " + (signal ? "true" : "false"));
        }
        return signal;
    }

    protected synchronized void reset() {
        Timber.i(Thread.currentThread().getId() + " reset");
        signal = false;
    }

    protected abstract void createComponentAndInjectDependencies();
}
