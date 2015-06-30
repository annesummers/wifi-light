package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.base.AndroidEventBus;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ApplicationLogger;
import com.giganticsheep.wifilight.ui.base.BaseApplication;
import com.giganticsheep.wifilight.base.BaseLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Module
public class BaseApplicationModule {
    protected final BaseApplication application;

    public BaseApplicationModule(BaseApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    BaseLogger provideBaseLogger() {
        return new ApplicationLogger(application);
    }

    @Provides
    @Singleton
    BaseApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return new AndroidEventBus();
    }

    @Provides
    @Singleton
    BaseApplication.FragmentFactory provideFragmentFactory() {
        return application.createFragmentFactory();
    }
}
