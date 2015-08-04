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
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */

@Module( includes = { BaseNetworkModule.class} )
public class NetworkModule {

    @Provides
    @ApplicationScope
    LightService provideService(RestAdapter restAdapter, LightNetwork lightNetwork) {
        MockRestAdapter mockRestAdapter = MockRestAdapter.from(restAdapter);
        mockRestAdapter.setErrorPercentage(100);
        return mockRestAdapter.create(LightService.class, new MockLightService(lightNetwork));
    }

    // TODO sort out code duplication with provideLightNetwork()
    @NonNull
    @Provides
    LightNetwork provideLightNetwork() {
        LightNetwork lightNetwork = new LightNetwork();

        GroupData testLocation = new GroupData();
        testLocation.name = Constants.TEST_LOCATION_LABEL;
        testLocation.id = Constants.TEST_LOCATION_ID;
        lightNetwork.add((Location) testLocation);

        GroupData testGroup = new GroupData();
        testGroup.name = Constants.TEST_GROUP_LABEL;
        testGroup.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) testGroup);

        GroupData testGroup2 = new GroupData();
        testGroup2.name = Constants.TEST_GROUP_LABEL2;
        testGroup2.id = Constants.TEST_GROUP_ID2;
        lightNetwork.add((Group) testGroup2);

        LightViewData testLight1 = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, testGroup.getId());
        lightNetwork.add(testLight1);

        LightViewData testLight2 = new LightViewData(Constants.TEST_ID2, Constants.TEST_LABEL2, true, testGroup.getId());
        lightNetwork.add(testLight2);

        LightViewData testLight3 = new LightViewData(Constants.TEST_ID3, Constants.TEST_LABEL3, true, testGroup2.getId());
        lightNetwork.add(testLight3);

        return lightNetwork;
    }
}
