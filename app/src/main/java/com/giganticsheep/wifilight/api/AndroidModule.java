package com.giganticsheep.wifilight.api;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */


import com.giganticsheep.wifilight.WifiLightApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AndroidModule {

    private final WifiLightApplication app;
    public AndroidModule(WifiLightApplication app) {
    this.app = app;
    }

    @Provides
    @Singleton
    WifiLightApplication provideApplication() {
        return app;
    }
}
