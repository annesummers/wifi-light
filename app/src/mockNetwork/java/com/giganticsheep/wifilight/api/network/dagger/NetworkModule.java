package com.giganticsheep.wifilight.api.network.dagger;

import com.giganticsheep.wifilight.api.network.BaseNetworkModule;
import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.MockLightService;
import com.giganticsheep.wifilight.dagger.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import retrofit.MockRestAdapter;
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
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        mockRestAdapter.setErrorPercentage(20);
        return mockRestAdapter.create(LightService.class, new MockLightService());
    }
}
