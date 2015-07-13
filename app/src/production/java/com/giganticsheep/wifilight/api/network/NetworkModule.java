package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.dagger.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = BaseNetworkModule.class )
public class NetworkModule {

    @Provides
    @ApplicationScope
    LightService provideService(RestAdapter restAdapter) {
        return restAdapter.create(LightService.class);
    }
}
