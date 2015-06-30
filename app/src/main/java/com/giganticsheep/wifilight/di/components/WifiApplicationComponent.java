package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.modules.BaseApplicationModule;
import com.giganticsheep.wifilight.di.modules.WifiApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Singleton
@Component(modules = { BaseApplicationModule.class, WifiApplicationModule.class } )
public interface WifiApplicationComponent extends BaseApplicationComponent {

    //Exposed to sub-graphs.
    NetworkDetails networkDetails();
   // BaseApplication.EventBus eventBus();
}
