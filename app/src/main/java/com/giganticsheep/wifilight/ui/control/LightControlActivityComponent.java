package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { LightControlActivityModule.class})//,
                   // LightControlDrawerFragmentModule.class})
public interface LightControlActivityComponent extends LightFragmentBase.Injector,
                                                        LightPresenterBase.Injector,
                                                        LightControlActivity.Injector {
   // void inject(LightControlActivity activity);
}