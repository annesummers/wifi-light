package com.giganticsheep.wifilight;

import android.util.Log;

import com.giganticsheep.wifilight.ui.HSVFragment;
import com.giganticsheep.wifilight.ui.rx.RXApplication;
import com.giganticsheep.wifilight.ui.rx.RXFragment;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends RXApplication {
    public static final String FRAGMENT_NAME_HSVFRAGMENT = "HSVFragment";
    private static final String INITIAL_API_KEY = "ce582d802a97f1a4b2e3bf43d1336113b7f96b0f402ad7264dbdf42ae5a37f9d";
    private static final String INITIAL_URL_STRING = "https://api.lifx.com";

    private String mAPIKey;
    private String mServerURL;

    private static WifiLightApplication mApplication;
    private String mBaseUrl;
    private String INITIAL_BASE_URL = "/v1beta1/lights";

    public static WifiLightApplication application() {
        return mApplication;
    }

    @Override
    public final void onCreate() {
        super.onCreate();

        mApplication = this;

        mAPIKey = INITIAL_API_KEY;
        mServerURL = INITIAL_URL_STRING;
        mBaseUrl = INITIAL_BASE_URL;
    }

    @Override
    protected final Observable<? extends RXFragment> createFragment(final String name) {
        if(name.equals(FRAGMENT_NAME_HSVFRAGMENT)) {
            return Observable.just(HSVFragment.newInstance(name));
        }

        return Observable.error(new Exception("Fragment does not exist"));
    }

    @Override
    public final String toString() {
        return "WifiLightApplication{" +
                "APIKey='" + mAPIKey + '\'' +
                '}';
    }

    public final void setAPIKey(final String aPIKey) {
        mAPIKey = aPIKey;
    }

    public final String aPIKey() {
        return mAPIKey;
    }

    public final String serverURL() {
        return mServerURL;
    }

    public void logWarn(String message) {
        Log.w(getString(R.string.app_name), message);
    }

    public void logError(String message) {
        Log.e(getString(R.string.app_name), message);
    }

    public void logInfo(String message) {
        Log.i(getString(R.string.app_name), message);
    }

    public void logDebug(String message) {
        Log.d(getString(R.string.app_name), message);
    }

    public String baseUrl() {
        return mBaseUrl;
    }
}
