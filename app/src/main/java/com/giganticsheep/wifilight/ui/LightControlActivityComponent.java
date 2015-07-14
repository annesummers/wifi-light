package com.giganticsheep.wifilight.ui;

import android.app.Activity;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
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
public interface LightControlActivityComponent extends LightFragmentBase.Injector,
                                                        LightPresenterBase.Injector,
                                                        DrawerAdapter.Injector {

    Activity activity();

    void inject(LightControlActivity activity);
}