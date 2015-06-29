package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.modules.BaseApplicationModule;
import com.giganticsheep.wifilight.ui.base.BaseActivity;
import com.giganticsheep.wifilight.ui.base.BaseApplication;
import com.giganticsheep.wifilight.ui.base.BaseLogger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = BaseApplicationModule.class)
public interface BaseApplicationComponent {
    void inject(BaseActivity baseActivity);

    BaseApplication application();
    BaseLogger baseLogger();
    BaseApplication.EventBus eventBus();
}
