package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.dagger.ApplicationScope;
import com.giganticsheep.wifilight.dagger.WifiLightModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module(includes = WifiLightModule.class)
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
