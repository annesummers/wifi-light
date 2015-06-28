package com.giganticsheep.wifilight.di.components;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.modules.NetworkDetailsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Singleton
@Component(modules = NetworkDetailsModule.class)
public interface NetworkDetailsComponent {
    NetworkDetails networkDetails();
}
