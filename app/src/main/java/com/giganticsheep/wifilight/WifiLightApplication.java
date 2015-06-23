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

    private static WifiLightApplication thisInstance;

    private String apiKey;
    private String serverURL;
    private String baseURL1;
    private String baseURL2;
    // TODO this is truly horrible

    private Bus bus = new Bus();

    /**
     * @return the singleton object that is this thisInstance
     */
    public static WifiLightApplication application() {
        return thisInstance;
    }

    @Override
    public final void onCreate() {
        super.onCreate();

        thisInstance = this;

        // TODO private api key
        this.apiKey = DEFAULT_API_KEY;//getString(R.string.DEFAULT_API_KEY);
        this.serverURL = DEFAULT_SERVER_STRING;
        this.baseURL1 = DEFAULT_URL_STRING1;
        this.baseURL2 = DEFAULT_URL_STRING2;
    }

    /**
     * Posts a message to the global message bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    public void postMessage(Object messageObject) {
        bus.post(messageObject);
    }

    public void registerForEvents(Object myClass) {
        bus.register(myClass);
    }

    public void unregisterForEvents(Object myClass) {
        bus.unregister(myClass);
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
     * @param apiKey a string representing the current API key
     */
    public final void setAPIKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return a String representing the current API key
     */
    public final String apiKey() {
        return apiKey;
    }

    /**
     * @return a String representing the current server URL
     */
    public final String serverURL() {
        return serverURL;
    }

    /**
     * @return a String representing the current base path URL
     */
    public final String baseURL1() {
        return baseURL1;
    }

    /**
     * @return a String representing the current base path URL
     */
    public final String baseURL2() {
        return baseURL2;
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
    public final String toString() {
        return "WifiLightApplication{" +
                "apiKey='" + apiKey + '\'' +
                ", serverURL='" + serverURL + '\'' +
                ", baseURL1='" + baseURL1 + '\'' +
                ", baseURL2='" + baseURL2 + '\'' +
                ", bus=" + bus +
                '}';
    }
}
