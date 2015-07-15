package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.TestModule;
import com.giganticsheep.wifilight.ui.ActivityScope;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { TestModule.class } )
public interface TestModelComponent {

    void inject(ModelTest wifiLightTest);
}