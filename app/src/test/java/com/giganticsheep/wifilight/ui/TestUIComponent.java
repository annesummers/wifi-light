package com.giganticsheep.wifilight.ui;

import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { TestUIModule.class } )
public interface TestUIComponent {

    void inject(UITest UITest);
}
