package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.WifiLightApplication;

import org.jetbrains.annotations.NonNls;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class Logger implements Serializable {

    @NonNls private static final char SPACE = ' ';
    @NonNls private static final String ERROR_LABEL = "ERROR :";

    private final String mClassName;

    @Inject
    public Logger(String className) {
        mClassName = className;
    }

    public final void logWarn(final String message) {
        WifiLightApplication.application().logWarn(mClassName + SPACE + message);
    }

    public final void error(final Throwable throwable) {
        WifiLightApplication.application().logError(toString() + SPACE + ERROR_LABEL + SPACE + throwable.getMessage());
    }

    public final void error(final String message) {
        WifiLightApplication.application().logError(mClassName + SPACE + ERROR_LABEL + SPACE  + message);
    }

    public final void logInfo(final String message) {
        WifiLightApplication.application().logInfo(mClassName + SPACE + message);
    }

    public final void debug(final String message) {
        WifiLightApplication.application().logDebug(mClassName + SPACE + message);
    }
}
