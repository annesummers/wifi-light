package com.giganticsheep.wifilight;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.dagger.HasComponent;

import org.jetbrains.annotations.NonNls;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends Application implements HasComponent<WifiLightAppComponent> {

    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    @Inject Timber.Tree loggerTree;

    private WifiLightAppComponent component;

    @Override
    public final void onCreate() {
        super.onCreate();

        buildComponentAndInject();

        if(BuildConfig.DEBUG ||
                BuildConfig.APPLICATION_ID.equals("com.giganticsheep.wifilight.mocknetwork") ||
                        BuildConfig.APPLICATION_ID.equals("com.giganticsheep.wifilight.mockapi")) {
            Timber.plant(loggerTree);
        }
    }

    @Override
    public WifiLightAppComponent getComponent() {
        return component;
    }

    @NonNull
    public NetworkDetails getNetworkDetails() {
        return new NetworkDetails(
                getResources().getString(R.string.DEFAULT_API_KEY),
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);
    }

    @NonNull
    public String getServerURL() {
        return DEFAULT_SERVER_STRING;
    }

    private void buildComponentAndInject() {
        component = WifiLightAppComponent.Initializer.init(this);
        component.inject(this);
    }

    /**
     * A tree which logs important information for crash reporting.
     **/
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
        }
    }
}
