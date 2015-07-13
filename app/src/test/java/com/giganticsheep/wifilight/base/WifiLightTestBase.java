package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.api.LightControl;

import javax.inject.Inject;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public abstract class WifiLightTestBase {
    protected final Logger logger;

    @Inject protected LightControl lightNetwork;
    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;
    @Inject protected FragmentFactory fragmentFactory;

    private boolean signal = false;

    protected WifiLightTestBase() {
        createComponentAndInjectDependencies();

        logger = new Logger(getClass().getName(), baseLogger);
    }

    public synchronized void signal() {
        logger.info(Thread.currentThread().getId() + " signal");
        signal = true;
    }

    public synchronized boolean isSignalled() {
        if(signal) {
            logger.info(Thread.currentThread().getId() + " signal == " + (signal ? "true" : "false"));
        }
        return signal;
    }

    protected synchronized void reset() {
        logger.info(Thread.currentThread().getId() + " reset");
        signal = false;
    }

    protected abstract void createComponentAndInjectDependencies();
}
