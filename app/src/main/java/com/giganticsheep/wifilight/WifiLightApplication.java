package com.giganticsheep.wifilight;

import android.util.Log;

import com.giganticsheep.wifilight.ui.HSVFragment;
import com.giganticsheep.wifilight.ui.rx.RXApplication;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Bus;

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
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    private String mAPIKey;
    private String mServerURL;
    private String mBaseUrl1;
    private String mBaseUrl2;

    private static WifiLightApplication mApplication;

    private Bus bus = new Bus();

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

        // TODO private api key
        mAPIKey = DEFAULT_API_KEY;//getString(R.string.DEFAULT_API_KEY);
        mServerURL = DEFAULT_SERVER_STRING;
        mBaseUrl1 = DEFAULT_URL_STRING1;
        mBaseUrl2 = DEFAULT_URL_STRING2;
    }

    public void postMessage(Object messageObject) {
        bus.post(messageObject);
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
    public final String baseUrl1() {
        return mBaseUrl1;
    }

    /**
     * @return a String representing the current base path URL
     */
    public final String baseUrl2() {
        return mBaseUrl2;
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
                ", BaseUrl1='" + mBaseUrl1 + '\'' +
                ", BaseUrl2='" + mBaseUrl2 + '\'' +
                " }";
    }
}
