package com.giganticsheep.wifilight.base;

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

    private final String className;

    @Inject BaseLogger baseLogger;

    public Logger(String className, BaseLogger baseLogger) {
        this.className = className;
        this.baseLogger = baseLogger;
    }

    public final void warn(final String message) {
        baseLogger.logWarn(className + SPACE + message);
    }

    public final void error(final Throwable throwable) {
        baseLogger.logError(toString() + SPACE + ERROR_LABEL + SPACE + throwable.getMessage());
    }

    public final void error(final String message) {
        baseLogger.logError(className + SPACE + ERROR_LABEL + SPACE + message);
    }

    public final void info(final String message) {
        baseLogger.logInfo(className + SPACE + message);
    }

    public final void debug(final String message) {
        baseLogger.logDebug(className + SPACE + message);
    }
}