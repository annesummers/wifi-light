package com.giganticsheep.wifilight.ui.locations;

import com.giganticsheep.wifilight.WifiLightAppComponent;
import com.giganticsheep.wifilight.ui.base.ComponentBase;
import com.giganticsheep.wifilight.ui.base.ActivityScope;

import dagger.Component;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/08/15. <p>
 * (*_*)
 */

@ActivityScope
@Component(
        dependencies = {WifiLightAppComponent.class},
        modules = { LightNetworkDrawerFragmentModule.class})
public interface LightNetworkDrawerFragmentComponent extends ComponentBase,
                                                            LightNetworkDrawerFragment.Injector,
                                                            LightNetworkClickListener.Injector,
                                                            LightNetworkPresenter.Injector,
                                                            LightNetworkAdapterBase.Injector {
}
