package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.util.TestEventBus;

import javax.inject.Inject;

import timber.log.Timber;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public abstract class WifiLightTestBase {

    @Inject protected LightControl lightControl;
    @Inject protected EventBus eventBus;
    @Inject protected FragmentFactory fragmentFactory;

    @Inject protected LightNetwork testLightNetwork;

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

    protected <T> T getCheckedEvent(Class<T> clazz) {
        Object event = ((TestEventBus)eventBus).popLastMessage();
        assertThat(event, instanceOf(clazz));

        return (T) event;
    }

    protected abstract void createComponentAndInjectDependencies();
}
