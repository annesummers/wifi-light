package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.di.Network;
import com.giganticsheep.wifilight.di.PerActivity;
import com.giganticsheep.wifilight.di.modules.BaseNetworkModule;
import com.giganticsheep.wifilight.di.modules.NetworkModule;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Network
@Component(modules = { NetworkModule.class, BaseNetworkModule.class })
public interface NetworkComponent {

    void inject(LightNetwork lightNetwork);
}
