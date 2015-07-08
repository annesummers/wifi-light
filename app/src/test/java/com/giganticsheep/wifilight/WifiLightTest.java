package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.util.TestLogger;

import javax.inject.Inject;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class WifiLightTest {
    //private final WifiLightTestComponent component;

    @Inject protected Logger logger;
    @Inject protected BaseLogger baseLogger;
    @Inject protected LightNetwork lightNetwork;
    @Inject protected EventBus eventBus;

    private boolean signal = false;

    public WifiLightTest() {
      //  component = DaggerWifiLightTestComponent.builder().build();//WifiLightTestComponent.Initializer.init();
       // component.inject(this);

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
}
