package com.giganticsheep.wifilight.dagger;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.ui.base.BaseApplication;
import com.giganticsheep.wifilight.util.AndroidEventBus;
import com.giganticsheep.wifilight.util.ApplicationLogger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module(includes = WifiLightAppModule.class)
public class WifiLightModule {
  /*  protected final WifiLightApplication application;
*/
    public WifiLightModule(WifiLightApplication application) { }

    @Provides
    @ApplicationScope
    BaseLogger provideBaseLogger(WifiLightApplication application) {
        return new ApplicationLogger(application);
    }

    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        return new AndroidEventBus();
    }

    @Provides
    @ApplicationScope
    FragmentFactory provideFragmentFactory(WifiLightApplication application) {
        return new FragmentFactory(application);
    }
}
