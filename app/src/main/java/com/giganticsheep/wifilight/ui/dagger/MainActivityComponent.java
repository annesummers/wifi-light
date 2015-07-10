package com.giganticsheep.wifilight.ui.dagger;

import com.giganticsheep.wifilight.dagger.WifiLightAppComponent;
import com.giganticsheep.wifilight.mvp.presenter.MainActivityPresenter;
import com.giganticsheep.wifilight.ui.MainActivity;
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