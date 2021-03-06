package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
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
 * Created by anne on 28/06/15.
 * (*_*)
 */
@Module
public class BaseNetworkModule {

    @Provides
    @ApplicationScope
    RestAdapter provideRestAdapter(@NonNull Endpoint endpoint,
                                   @NonNull OkHttpClient client,
                                   @NonNull Gson gson) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(endpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @NonNull
    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @NonNull
    @Provides
    @ApplicationScope
    Gson provideGson() {
        return new GsonBuilder()
                .create();
    }

    @NonNull
    @Provides
    @ApplicationScope
    Endpoint provideEndpoint(@ServerURL String serverURL) {
        return Endpoints.newFixedEndpoint(serverURL);
    }

    // TODO variable log levels
}
