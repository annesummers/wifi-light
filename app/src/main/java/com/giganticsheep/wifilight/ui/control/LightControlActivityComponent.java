package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ComponentBase;
import com.giganticsheep.wifilight.ui.base.ActivityModule;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.status.light.LightStatusFragment;
import com.giganticsheep.wifilight.ui.status.light.LightStatusPresenter;

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
                                                        LightFragmentBase.Injector,
                                                        LightPresenterBase.Injector,
                                                        LightStatusFragment.Injector,
                                                        LightStatusPresenter.Injector,
                                                        LightControlActivity.Injector { }