package com.giganticsheep.wifilight.util;

import com.giganticsheep.wifilight.base.BaseLogger;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class TestLogger implements BaseLogger {

    private final String name;

    public TestLogger(String name) {
        this.name = name;
    }

    public final void logWarn(final String message) {
       // Log.w(name, message);
        System.err.println(message);
    }

    public final void logError(final String message) {
        //Log.e(name, message);
        System.err.println(message);
    }

    public final void logInfo(final String message) {
        //Log.i(name, message);
        System.err.println(message);
    }

    public final void logDebug(final String message) {
        //Log.d(name, message);
        System.err.println(message);
    }
}
