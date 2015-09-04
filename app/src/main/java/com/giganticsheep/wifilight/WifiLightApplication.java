package com.giganticsheep.wifilight;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.base.dagger.HasComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class WifiLightApplication extends Application implements HasComponent<WifiLightAppComponent> {

    @Inject Timber.Tree loggerTree;

    private SharedPreferences preferences;

    private WifiLightAppComponent component;

    @Override
    public final void onCreate() {
        super.onCreate();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        buildComponentAndInject();

        //TODO sort out logging
        if(BuildConfig.DEBUG ||
                BuildConfig.APPLICATION_ID.equals("com.giganticsheep.wifilight.mocknetwork") ||
                        BuildConfig.APPLICATION_ID.equals("com.giganticsheep.wifilight.mockapi")) {
            Timber.plant(loggerTree);
        }

        String preference_server = getString(R.string.preference_key_server);

        if(preferences.getString(preference_server, "").equals("")) {
            SharedPreferences.Editor preferencesEditor = preferences.edit();

            preferencesEditor.putString(preference_server, getResources().getString(R.string.default_server));
            preferencesEditor.putString(getString(R.string.preference_key_api_key), getResources().getString(R.string.default_api_key));
            preferencesEditor.putString(getString(R.string.preference_key_url1), getResources().getString(R.string.default_url1));
            preferencesEditor.putString(getString(R.string.preference_key_url2), getResources().getString(R.string.default_url2));

            preferencesEditor.apply();
        }
    }

    @Override
    public WifiLightAppComponent getComponent() {
        return component;
    }

    @NonNull
    public NetworkDetails getNetworkDetails() {
        String preference_api_key = getString(R.string.preference_key_api_key);
        String preference_url1 = getString(R.string.preference_key_url1);
        String preference_url2 = getString(R.string.preference_key_url2);

        return new NetworkDetails(
                preferences.getString(preference_api_key, getString(R.string.default_api_key)),
                preferences.getString(preference_url1, getString(R.string.default_url1)),
                preferences.getString(preference_url2, getString(R.string.default_url2)));
    }

    @NonNull
    public String getServerURL() {
        return preferences.getString(getString(R.string.preference_key_server), getString(R.string.default_server));
    }

    @NonNull
    public SharedPreferences getPreferences() {
        return preferences;
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
