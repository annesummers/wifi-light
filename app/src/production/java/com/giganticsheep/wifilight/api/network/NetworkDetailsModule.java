package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.base.dagger.WifiLightModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 08/07/15.
 * (*_*)
 */

@Module(includes = WifiLightModule.class)
public class NetworkDetailsModule {

    @NonNull
    @Provides
    @ApplicationScope
    NetworkDetails provideNetworkDetails(@NonNull WifiLightApplication application) {
        return application.getNetworkDetails();
    }

    @NonNull
    @Provides
    @ApplicationScope
    @ServerURL
    String provideServerURL(@NonNull WifiLightApplication application) {
        return application.getServerURL();
    }
}
