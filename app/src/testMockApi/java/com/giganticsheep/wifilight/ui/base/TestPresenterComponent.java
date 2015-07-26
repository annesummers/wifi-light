package com.giganticsheep.wifilight.ui.base;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.TestModule;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { TestModule.class } )
interface TestPresenterComponent extends LightPresenterBase.Injector {

    void inject(LightPresenterTestBase presenterTest);
}