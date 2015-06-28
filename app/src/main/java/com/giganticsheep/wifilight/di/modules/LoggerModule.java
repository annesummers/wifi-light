package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.ui.rx.BaseApplication;
import com.giganticsheep.wifilight.ui.rx.BaseLogger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module
public class LoggerModule {
    BaseApplication application;

    public LoggerModule(BaseApplication application) {
        this.application = application;
    }

    @Singleton
    @Provides
    BaseLogger provideBaseLogger() {
        return new BaseLogger(application);
    }
}
