package com.giganticsheep.wifilight;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 09/07/15.
 * (*_*)
 */

@Module
public class WifiLightAppModule {
    protected final WifiLightApplication application;

    public WifiLightAppModule() {
        this.application = null;
    }

    public WifiLightAppModule(WifiLightApplication application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    WifiLightApplication provideApplication() {
        return application;
    }
}
