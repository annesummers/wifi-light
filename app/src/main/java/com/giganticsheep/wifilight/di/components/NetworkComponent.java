package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.di.PerActivity;
import com.giganticsheep.wifilight.di.modules.NetworkModule;

import dagger.Component;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@PerActivity
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    void inject(LightNetwork lightNetwork);

    //LightService lightService();
}
