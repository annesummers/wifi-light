package com.giganticsheep.wifilight.ui;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.fragment.LightFragment;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { MainActivityModule.class })
public interface MainActivityComponent extends LightFragment.Injector {

    void inject(MainActivity activity);
}