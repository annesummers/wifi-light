package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.WifiLightApplication;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Logging {
    public void logWarn(final String message) {
        WifiLightApplication.application().logWarn(toString() + " " + message);
    }

    public void logError(String message) {
        WifiLightApplication.application().logError(toString() + " " + message);
    }

    public void logInfo(String message) {
        WifiLightApplication.application().logInfo(toString() + " " + message);
    }

    public void logDebug(String message) {
        WifiLightApplication.application().logDebug(toString() + " " + message);
    }
}
