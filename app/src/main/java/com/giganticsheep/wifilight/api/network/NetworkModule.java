package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = BaseNetworkModule.class )
public class NetworkModule {
    private final String serverURL = "https://api.lifx.com";

    /*public NetworkModule(String serverURL) {
        this.serverURL = serverURL;
    }
*/
    @Provides
    @ApplicationScope
    LightService provideService(RestAdapter restAdapter) {
        return restAdapter.create(LightService.class);
    }

    @Provides
    @ApplicationScope
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(serverURL);
    }
}
