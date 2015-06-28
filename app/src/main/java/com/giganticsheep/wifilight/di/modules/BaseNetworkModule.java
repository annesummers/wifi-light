package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.di.Network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
@Module
public class BaseNetworkModule {

    @Provides
    @Network
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
    @Network
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Network
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }
}
