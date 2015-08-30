package com.giganticsheep.wifilight.base.dagger;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.WifiLightAppModule;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.FragmentFactory;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.util.AndroidErrorStrings;
import com.giganticsheep.wifilight.util.AndroidEventBus;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module(includes = WifiLightAppModule.class)
public class WifiLightModule {

    private WifiLightApplication application;
  
    public WifiLightModule(WifiLightApplication application) {
        this.application = application;
    }

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

    @NonNull
    @Provides
    @ApplicationScope
    Timber.Tree provideTree() {
        return new Timber.DebugTree();
    }

    @NonNull
    @Provides
    @ApplicationScope
    Resources provideResources() {
        return application.getResources();
    }

    @NonNull
    @Provides
    @ApplicationScope
    ErrorStrings provideErrorStrings(Resources resources) {
        return new AndroidErrorStrings(resources);
    }
}
