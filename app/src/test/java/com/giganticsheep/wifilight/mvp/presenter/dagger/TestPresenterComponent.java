package com.giganticsheep.wifilight.mvp.presenter.dagger;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.dagger.TestModule;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterTestBase;
import com.giganticsheep.wifilight.ui.dagger.ActivityScope;

import dagger.Component;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightTestsComponent.class},
        modules = { TestModule.class } )
public interface TestPresenterComponent extends LightPresenterBase.Injector {

    void inject(LightPresenterTestBase presenterTest);
}