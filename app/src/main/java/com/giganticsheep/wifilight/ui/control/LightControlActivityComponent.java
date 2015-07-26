package com.giganticsheep.wifilight.ui.control;

import android.app.Activity;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ActivityScope;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;

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
                                                        DrawerFragment.Injector,
                                                        DrawerAdapter.Injector,
                                                        LightNetworkPresenter.Injector {

    Activity activity();

    void inject(LightControlActivity activity);
}