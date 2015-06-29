package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.NetworkDetails;
import com.giganticsheep.wifilight.ui.base.BaseApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */

@Module(includes = BaseApplicationModule.class)
public class WifiApplicationModule {

    @Provides
    @Singleton
    NetworkDetails provideNetworkDetails(BaseApplication application) {
        return ((WifiLightApplication) application).getNetworkDetails();
    }
}
