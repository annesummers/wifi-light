package com.giganticsheep.wifilight;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
@Module
public class WifiLightAppModule {

    @NonNull
    protected final WifiLightApplication application;

    public WifiLightAppModule(@NonNull final WifiLightApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    SharedPreferences providePreferences() {
        return application.getPreferences();
    }
}
