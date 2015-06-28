package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.api.network.LightService;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = BaseNetworkModule.class )
public class NetworkModule {
    private final String serverURL;

    //private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";


    public NetworkModule(String serverURL) {
        this.serverURL = serverURL;
    }

    @Provides
    LightService provideService(RestAdapter restAdapter) {
        return restAdapter.create(LightService.class);
    }

    @Provides
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(serverURL);
    }
}
