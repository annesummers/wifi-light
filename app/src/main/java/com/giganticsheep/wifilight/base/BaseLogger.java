package com.giganticsheep.wifilight.base;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public interface BaseLogger {
    void logWarn(final String message);
    void logError(final String message);
    void logInfo(final String message);
    void logDebug(final String message);
}
