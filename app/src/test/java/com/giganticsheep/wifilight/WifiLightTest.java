package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.Logger;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
public class WifiLightTest {
    protected final Logger logger;
    protected final BaseLogger baseLogger;

    private boolean signal = false;

    public WifiLightTest() {
        baseLogger = new TestLogger("WifiLightTest");
        logger = new Logger(getClass().getName(), baseLogger);
    }

    public synchronized void signal() {
        signal = true;
    }

    public synchronized boolean isSignalled() {
        return signal;
    }

    public synchronized void reset() {
        signal = false;
    }
}
