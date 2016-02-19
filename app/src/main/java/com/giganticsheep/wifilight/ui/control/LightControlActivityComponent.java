package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.base.ComponentBase;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { ActivityModule.class})
public interface LightControlActivityComponent extends ComponentBase,
                                                        LightControlScreenGroup.Injector,
                                                        LightScreen.Injector,
                                                        LightControlActivity.Injector { }