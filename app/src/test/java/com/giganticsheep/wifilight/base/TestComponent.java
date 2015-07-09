package com.giganticsheep.wifilight.base;

import com.giganticsheep.wifilight.WifiLightTestModule;
import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.ui.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { WifiLightTestModule.class } )
public interface TestComponent {

    void inject(WifiLightTest wifiLightTest);
}