package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.di.ApplicationScope;
import com.giganticsheep.wifilight.di.ServerURL;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module(includes = WifiLightAppModule.class)
public class NetworkDetailsModule {

    @Provides
    @ApplicationScope
    NetworkDetails provideNetworkDetails(WifiLightApplication application) {
        return application.getNetworkDetails();
    }

    @Provides
    @ApplicationScope
    @ServerURL
    String provideServerURL(WifiLightApplication application) {
        return application.getServerURL();
    }
}
