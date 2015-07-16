package com.giganticsheep.wifilight;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */
@Module
public class WifiLightAppModule {

    @NotNull
    protected final WifiLightApplication application;

    public WifiLightAppModule(@NonNull final WifiLightApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }
}
