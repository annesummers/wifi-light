package com.giganticsheep.wifilight.ui.dagger;

import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.giganticsheep.wifilight.ui.fragment.LightFragmentBase;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { LightControlActivityModule.class })
public interface LightControlActivityComponent extends LightFragmentBase.Injector {

    void inject(LightControlActivity activity);
}