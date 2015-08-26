package com.giganticsheep.wifilight.api.network.test;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.GroupData;
import com.giganticsheep.wifilight.api.network.LocationData;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.util.Constants;

import dagger.Module;
import dagger.Provides;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 29/07/15. <p>
 * (*_*)
 */
@Module
public class TestLightNetworkModule {

    @NonNull
    @Provides
    LightNetwork provideLightNetwork() {
        LightNetwork lightNetwork = new LightNetwork();

        LocationData testLocation = new LocationData();
        testLocation.name = Constants.TEST_LOCATION_LABEL;
        testLocation.id = Constants.TEST_LOCATION_ID;

        GroupData testGroup = new GroupData();
        testGroup.name = Constants.TEST_GROUP_LABEL;
        testGroup.id = Constants.TEST_GROUP_ID;
        testLocation.addGroup(testGroup);

        Light testLight1 = new MockLight(Constants.TEST_ID, Constants.TEST_LABEL,
                testLocation.getId(), testGroup.getId());
        testLocation.getGroup(testLight1.getGroupId()).addLight(testLight1);

        Light testLight2 = new MockLight(Constants.TEST_ID2, Constants.TEST_LABEL2,
                testLocation.getId(), testGroup.getId());
        testLocation.getGroup(testLight2.getGroupId()).addLight(testLight2);

        GroupData testGroup2 = new GroupData();
        testGroup2.name = Constants.TEST_GROUP_LABEL2;
        testGroup2.id = Constants.TEST_GROUP_ID2;
        testLocation.addGroup(testGroup2);

        Light testLight3 = new MockLight(Constants.TEST_ID3, Constants.TEST_LABEL3,
                testLocation.getId(), testGroup2.getId());
        testLocation.getGroup(testLight3.getGroupId()).addLight(testLight3);

        lightNetwork.addLightLocation(testLocation);

        LocationData testLocation2 = new LocationData();
        testLocation.name = Constants.TEST_LOCATION_LABEL2;
        testLocation.id = Constants.TEST_LOCATION_ID2;

        GroupData testGroup3 = new GroupData();
        testGroup.name = Constants.TEST_GROUP_LABEL3;
        testGroup.id = Constants.TEST_GROUP_ID3;
        testLocation2.addGroup(testGroup3);

        Light testLight4 = new MockLight(Constants.TEST_ID4, Constants.TEST_LABEL4,
                testLocation2.getId(), testGroup3.getId());
        testLocation2.getGroup(testLight4.getGroupId()).addLight(testLight4);

        lightNetwork.addLightLocation(testLocation2);

        return lightNetwork;
    }
}
