package com.giganticsheep.wifilight;

import android.util.Log;

import com.giganticsheep.wifilight.ui.HSVFragment;
import com.giganticsheep.wifilight.ui.rx.RXApplication;
import com.giganticsheep.wifilight.ui.rx.RXFragment;

import org.jetbrains.annotations.NonNls;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends RXApplication {
    @NonNls public static final String FRAGMENT_NAME_HSVFRAGMENT = "HSVFragment";

    @NonNls private static final String DEFAULT_API_KEY = "c5e3c4b06448baa75d3a849b7cdb70930e4b95e9e7160a4415c49bf03ffa45f8";
    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING = "/v1beta1/lights";

    private String mAPIKey;
    private String mServerURL;
    private String mBaseUrl;

    private static WifiLightApplication mApplication;

    /**
     * @return the singleton object that is this application
     */
    public static WifiLightApplication application() {
        return mApplication;
    }

    @Override
    public final void onCreate() {
        super.onCreate();

        mApplication = this;

        mAPIKey = DEFAULT_API_KEY;//getString(R.string.DEFAULT_API_KEY);
        mServerURL = DEFAULT_SERVER_STRING;
        mBaseUrl = DEFAULT_URL_STRING;
    }

    /**
     * @param name the name of the fragment to create
     * @return the Observable to subscribe to
     */
    @Override
    protected final Observable<? extends RXFragment> createFragment(final String name) {
        if(name.equals(FRAGMENT_NAME_HSVFRAGMENT)) {
            return Observable.just(HSVFragment.newInstance(name));
        }

        return Observable.error(new Exception("Fragment does not exist"));
    }

    /**
     * @param aPIKey a string representing the current API key
     */
    public final void setAPIKey(final String aPIKey) {
        mAPIKey = aPIKey;
    }

    /**
     * @return a String representing the current API key
     */
    public final String aPIKey() {
        return mAPIKey;
    }

    /**
     * @return a String representing the current server URL
     */
    public final String serverURL() {
        return mServerURL;
    }

    /**
     * @return a String representing the current base path URL
     */
    public final String baseUrl() {
        return mBaseUrl;
    }

    // Logging

    public final void logWarn(final String message) {
        Log.w(getString(R.string.app_name), message);
    }

    public final void logError(final String message) {
        Log.e(getString(R.string.app_name), message);
    }

    public final void logInfo(final String message) {
        Log.i(getString(R.string.app_name), message);
    }

    public final void logDebug(final String message) {
        Log.d(getString(R.string.app_name), message);
    }

    @Override
    public String toString() {
        return "WifiLightApplication{" +
                " APIKey='" + mAPIKey + '\'' +
                ", ServerURL='" + mServerURL + '\'' +
                ", BaseUrl='" + mBaseUrl + '\'' +
                " }";
    }
}
