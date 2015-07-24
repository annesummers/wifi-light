package com.giganticsheep.wifilight.base.dagger;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.WifiLightAppModule;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.util.AndroidEventBus;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module(includes = WifiLightAppModule.class)
public class WifiLightModule {
  
    public WifiLightModule(WifiLightApplication application) { }

    public WifiLightModule() { }

    @NonNull
    @Provides
    @ApplicationScope
    EventBus provideEventBus() {
        return new AndroidEventBus();
    }

    @NonNull
    @Provides
    @ApplicationScope
    FragmentFactory provideFragmentFactory(WifiLightApplication application) {
        return new FragmentFactory(application);
    }
}
