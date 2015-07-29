package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.control.LightNetwork;
import com.giganticsheep.wifilight.ui.control.LightViewData;
import com.giganticsheep.wifilight.util.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = BaseNetworkModule.class )
public class NetworkModule {

    @Provides
    @ApplicationScope
    LightService provideService(RestAdapter restAdapter) {
        return null;
    }
}
