package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.di.PerActivity;
import com.giganticsheep.wifilight.di.modules.BaseActivityModule;
import com.giganticsheep.wifilight.di.modules.MainActivityModule;
import com.giganticsheep.wifilight.ui.fragment.LightFragment;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@PerActivity
@Component(
        dependencies = {WifiApplicationComponent.class},
        modules = {BaseActivityModule.class,
                    MainActivityModule.class})
public interface MainActivityComponent extends BaseActivityComponent {

    void inject(LightFragment lightFragment);
}