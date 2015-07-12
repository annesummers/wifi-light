package com.giganticsheep.wifilight.util;

import android.util.Log;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.base.BaseLogger;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
public class ApplicationLogger implements BaseLogger {

    private final BaseApplication application;

    public ApplicationLogger(BaseApplication application) {
        this.application = application;
    }

    public final void logWarn(final String message) {
        Log.w(application.getString(R.string.app_name), message);
    }

    public final void logError(final String message) {
        Log.e(application.getString(R.string.app_name), message);
    }

    public final void logInfo(final String message) {
        Log.i(application.getString(R.string.app_name), message);
    }

    public final void logDebug(final String message) {
        Log.d(application.getString(R.string.app_name), message);
    }
}
