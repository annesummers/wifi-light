package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.modules.BaseApplicationModule;
import com.giganticsheep.wifilight.ui.rx.BaseActivity;
import com.giganticsheep.wifilight.ui.rx.BaseApplication;

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
    BaseApplication.EventBus eventBus();
   // BaseApplication.FragmentFactory fragmentFactory();
}
