package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.api.network.LightNetwork;

import javax.inject.Inject;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public abstract class WifiLightTest {
    protected TestComponent component;

    protected final Logger logger;

    @Inject protected LightNetwork lightNetwork;
    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;

    private boolean signal = false;

    public WifiLightTest() {
        component = DaggerTestComponent.builder()
                .wifiLightTestsComponent(WifiLightTestsComponent.Initializer.init())
                .build();

        injectDependencies();

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

    public synchronized void reset() {
        logger.info(Thread.currentThread().getId() + " reset");
        signal = false;
    }

    protected abstract void injectDependencies();
}
