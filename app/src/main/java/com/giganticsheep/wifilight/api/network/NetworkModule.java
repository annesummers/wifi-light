package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

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

@Module(
        injects = {
                LightNetwork.class
        },
        complete = false,
        library = true
)

public class NetworkModule {

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(WifiLightApplication.application().serverURL());
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint,
                                   OkHttpClient client,
                                   Gson gson) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    @Singleton
    LightService provideService(RestAdapter restAdapter) {
        return restAdapter.create(LightService.class);
    }
}
