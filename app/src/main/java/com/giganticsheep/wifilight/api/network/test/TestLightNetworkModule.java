package com.giganticsheep.wifilight.api.network.test;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.GroupImpl;
import com.giganticsheep.wifilight.api.network.LocationImpl;
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

        LocationImpl testLocation = new LocationImpl(Constants.TEST_LOCATION_ID,
                                                    Constants.TEST_LOCATION_LABEL);

        GroupImpl testGroup = new GroupImpl(Constants.TEST_GROUP_ID,
                                            Constants.TEST_GROUP_LABEL);
        testLocation.addGroup(testGroup);

        Light testLight1 = new MockLight(Constants.TEST_ID,
                                        Constants.TEST_LABEL,
                                        testLocation.getId(),
                                        testGroup.getId());
        testLocation.getGroup(testLight1.getGroupId()).addLight(testLight1);

        Light testLight2 = new MockLight(Constants.TEST_ID2,
                                        Constants.TEST_LABEL2,
                                        testLocation.getId(),
                                        testGroup.getId());
        testLocation.getGroup(testLight2.getGroupId()).addLight(testLight2);

        GroupImpl testGroup2 = new GroupImpl(Constants.TEST_GROUP_ID2,
                                            Constants.TEST_GROUP_LABEL2);
        testLocation.addGroup(testGroup2);

        Light testLight3 = new MockLight(Constants.TEST_ID3,
                                        Constants.TEST_LABEL3,
                                        testLocation.getId(),
                                        testGroup2.getId());
        testLocation.getGroup(testLight3.getGroupId()).addLight(testLight3);

        lightNetwork.addLightLocation(testLocation);

        LocationImpl testLocation2 = new LocationImpl(Constants.TEST_LOCATION_ID2,
                                                     Constants.TEST_LOCATION_LABEL2);

        GroupImpl testGroup3 = new GroupImpl(Constants.TEST_GROUP_ID3,
                                            Constants.TEST_GROUP_LABEL3);
        testLocation2.addGroup(testGroup3);

        Light testLight4 = new MockLight(Constants.TEST_ID4,
                                        Constants.TEST_LABEL4,
                                        testLocation2.getId(),
                                        testGroup3.getId());
        testLocation2.getGroup(testLight4.getGroupId()).addLight(testLight4);

        lightNetwork.addLightLocation(testLocation2);

        return lightNetwork;
    }
}
