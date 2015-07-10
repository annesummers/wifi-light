package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.WifiLightTestsComponent;
import com.giganticsheep.wifilight.base.TestModule;
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
public interface TestPresenterComponent extends LightPresenter.Injector {

    void inject(LightPresenterTest presenterTest);
}