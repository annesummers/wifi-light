package com.giganticsheep.wifilight.api.network.dagger;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.api.network.ServerURL;
import com.giganticsheep.wifilight.dagger.ApplicationScope;

import org.jetbrains.annotations.NonNls;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module
public class NetworkDetailsModule {
    @NonNls private static final String DEFAULT_API_KEY = "123456789abcdef";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    @NonNls private static final String DEFAULT_SERVER_URL = "mock:///";

    @Provides
    @ApplicationScope
    NetworkDetails provideNetworkDetails() {
        return new NetworkDetails(DEFAULT_API_KEY,
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);
    }

    @Provides
    @ApplicationScope
    @ServerURL
    String provideServerURL() {
        return DEFAULT_SERVER_URL;
    }
}
