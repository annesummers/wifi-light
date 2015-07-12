package com.giganticsheep.wifilight.ui.dagger;

import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.UITestBase;

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

    void inject(UITestBase UITest);
}
