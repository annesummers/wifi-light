package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.network.test.TestLightNetworkModule;
import com.giganticsheep.wifilight.ui.control.LightNetwork;

import dagger.Module;
import dagger.Provides;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = { BaseNetworkModule.class,
                      TestLightNetworkModule.class} )
public class NetworkModule {

    @Provides
    @ApplicationScope
    LightService provideService(@NonNull RestAdapter restAdapter,
                                @NonNull MockLightService lightService) {
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        mockRestAdapter.setErrorPercentage(100);
        return mockRestAdapter.create(LightService.class, lightService);
    }

    @Provides
    @ApplicationScope
    MockLightService provideMockService(@NonNull LightNetwork lightNetwork) {
        return new MockLightService(lightNetwork);
    }
}
