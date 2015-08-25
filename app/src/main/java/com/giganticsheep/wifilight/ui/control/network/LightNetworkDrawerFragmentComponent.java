package com.giganticsheep.wifilight.ui.control.network;

import com.giganticsheep.wifilight.WifiLightAppComponent;
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
public interface LightNetworkDrawerFragmentComponent extends LightNetworkDrawerFragment.Injector,
                                                            OnNetworkItemClickListener.Injector,
                                                            LightNetworkPresenter.Injector,
                                                            LightNetworkAdapterBase.Injector {
}
