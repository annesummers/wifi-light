package com.giganticsheep.wifilight;

import android.util.Log;

import com.giganticsheep.wifilight.api.AndroidModule;
import com.giganticsheep.wifilight.ui.LightColourFragment;
import com.giganticsheep.wifilight.ui.LightDetailsFragment;
import com.giganticsheep.wifilight.ui.LightEffectsFragment;
import com.giganticsheep.wifilight.ui.rx.RXApplication;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Bus;

import org.jetbrains.annotations.NonNls;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends RXApplication {
    //@NonNls public static final String FRAGMENT_NAME_LIGHT_COLOUR_FRAGMENT = "Colour";
    //@NonNls public static final String FRAGMENT_NAME_LIGHT_EFFECT_FRAGMENT = "Effects";
    //@NonNls public static final String FRAGMENT_NAME_LIGHT_DETAILS_FRAGMENT = "Details";

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

    private final Bus bus = new Bus();

    private ObjectGraph objectGraph;

    /**
     * @return the singleton object that is this thisInstance
     */
    public static WifiLightApplication application() {
        return thisInstance;
    }

    @Override
    public final void onCreate() {
        super.onCreate();

        objectGraph = objectGraph.create(getModules().toArray());
        //objectGraph.inject(this);

        thisInstance = this;

        // TODO private api key
        this.apiKey = DEFAULT_API_KEY;//getString(R.string.DEFAULT_API_KEY);
        this.serverURL = DEFAULT_SERVER_STRING;
        this.baseURL1 = DEFAULT_URL_STRING1;
        this.baseURL2 = DEFAULT_URL_STRING2;
    }

    public ObjectGraph getApplicationGraph() {
        return objectGraph;
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                (Object)new AndroidModule(this)
        );
    }

    public void inject(Object object) {
        objectGraph.inject(object);
    }

    /**
     * Posts a message to the global message bus.  Classes must register to receive messages
     * and much subscribe to  a specific message to receive it
     *
     * @param messageObject the object to post to the bus
     */
    public Observable postMessage(final Object messageObject) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    bus.post(messageObject);
                } catch(Exception e) {
                    subscriber.onError(e);
                }

                subscriber.onNext(messageObject);
                subscriber.onCompleted();
            }
        }).subscribeOn(AndroidSchedulers.mainThread());
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
    public final Observable<? extends RXFragment> createFragmentAsync(final String name) {
        if(name.equals(getString(R.string.fragment_name_light_colour))) {
            return Observable.just(LightColourFragment.newInstance(name));
        }

        if(name.equals(getString(R.string.fragment_name_light_effects))) {
            return Observable.just(LightEffectsFragment.newInstance(name));
        }

        if(name.equals(getString(R.string.fragment_name_light_details))) {
            return Observable.just(LightDetailsFragment.newInstance(name));
        }

        return Observable.error(new Exception("Fragment does not exist"));
    }

    /**
     * @param name the name of the fragment to create
     * @return the Observable to subscribe to
     */
    @Override
    public final RXFragment createFragment(final String name) throws Exception {
        if(name.equals(getString(R.string.fragment_name_light_colour))) {
            return LightColourFragment.newInstance(name);
        }

        if(name.equals(getString(R.string.fragment_name_light_effects))) {
            return LightEffectsFragment.newInstance(name);
        }

        if(name.equals(getString(R.string.fragment_name_light_details))) {
            return LightDetailsFragment.newInstance(name);
        }

        throw new Exception("Fragment does not exist");
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
