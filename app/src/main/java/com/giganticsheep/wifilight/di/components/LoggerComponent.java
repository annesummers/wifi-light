package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.di.modules.LoggerModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = LoggerModule.class)
public interface LoggerComponent {
    void inject(Logger logger);
}
