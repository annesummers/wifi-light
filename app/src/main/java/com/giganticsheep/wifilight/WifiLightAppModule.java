package com.giganticsheep.wifilight;

import android.support.annotation.Nullable;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */

@Module
public class WifiLightAppModule {
    @Nullable
    protected final WifiLightApplication application;

    public WifiLightAppModule() {
        this.application = null;
    }

    public WifiLightAppModule(WifiLightApplication application) {
        this.application = application;
    }

    @Nullable
    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }
}
