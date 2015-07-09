package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.util.AndroidEventBus;
import com.giganticsheep.wifilight.util.ApplicationLogger;
import com.giganticsheep.wifilight.util.TestEventBus;
import com.giganticsheep.wifilight.util.TestLogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module
public class WifiLightModule {

    private final WifiLightApplication application;

    // for the app component
    public WifiLightModule(WifiLightApplication application) {
        this.application = application;
    }

    // for the test component
    public WifiLightModule() {
        application = null;
    }

    @Provides
    @ApplicationScope
    BaseLogger provideBaseLogger() {
        if(application != null) {
            return new ApplicationLogger(application);
        } else {
            return new TestLogger("WifiLight Tests");
        }
    }

    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        if(application != null) {
            return new AndroidEventBus();
        } else {
            return new TestEventBus();
        }
    }

    @Provides
    @ApplicationScope
    FragmentFactory provideFragmentFactory() {
        if(application != null) {
            return new FragmentFactory(application);
        } else {
            return null;
        }
    }
}
