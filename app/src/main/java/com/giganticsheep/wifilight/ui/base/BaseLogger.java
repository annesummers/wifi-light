package com.giganticsheep.wifilight.ui.base;

import android.util.Log;

import com.giganticsheep.wifilight.R;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
public class BaseLogger {

    private BaseApplication application;

    public BaseLogger(BaseApplication application) {
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
