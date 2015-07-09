package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.base.BaseApplication;
import com.giganticsheep.wifilight.util.TestEventBus;
import com.giganticsheep.wifilight.util.TestFragmentFactory;
import com.giganticsheep.wifilight.util.TestLogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module
public class WifiLightModule {

    // for the app component
    public WifiLightModule(WifiLightApplication app) { }

    // for the test component
    public WifiLightModule() { }

    @Provides
    @ApplicationScope
    BaseLogger provideBaseLogger() {
        return new TestLogger("Test");
    }

    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        return new TestEventBus();
    }

    @Provides
    @ApplicationScope
    BaseApplication.FragmentFactory provideFragmentFactory() {
        return new TestFragmentFactory();
    }
}
