package com.giganticsheep.wifilight.ui.base;

import com.giganticsheep.wifilight.ui.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.TestModule;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.control.LightNetworkPresenter;
import com.giganticsheep.wifilight.ui.control.LightNetworkPresenterTest;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { TestModule.class } )
public interface TestPresenterComponent extends LightPresenterBase.Injector,
                                                LightNetworkPresenter.Injector {

    void inject(LightPresenterTestBase presenterTest);
    void inject(LightNetworkPresenterTest presenterTest);
}