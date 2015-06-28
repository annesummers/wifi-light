package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.api.network.NetworkDetails;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module
public class NetworkDetailsModule {

    private final NetworkDetails networkDetails;

    public NetworkDetailsModule(NetworkDetails networkDetails) {
        this.networkDetails = networkDetails;
    }

    @Provides
    @Singleton
    NetworkDetails provideNetworkDetails() {
        return networkDetails;
    }
}
