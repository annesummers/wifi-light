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
    // TODO let's sort this null application

    @Nullable
    protected final WifiLightApplication application;

    public WifiLightAppModule() {
        this.application = null;
    }

    public WifiLightAppModule(final WifiLightApplication application) {
        this.application = application;
    }

    @Nullable
    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }
}
