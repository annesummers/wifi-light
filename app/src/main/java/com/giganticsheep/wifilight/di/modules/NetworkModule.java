package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.di.PerActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

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

@Module
public class NetworkModule {
    private final String serverURL;

    private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";

    public NetworkModule(String serverURL) {
        this.serverURL = serverURL;
    }

    @Provides
    @PerActivity
    LightService provideLightService(RestAdapter restAdapter) {
        return restAdapter.create(LightService.class);
    }

    @Provides
    @PerActivity
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
    @PerActivity
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(serverURL);
    }

    @Provides
    @PerActivity
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerActivity
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }
}
