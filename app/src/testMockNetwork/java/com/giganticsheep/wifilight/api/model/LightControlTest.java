package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.FetchedGroupsEvent;
import com.giganticsheep.wifilight.api.FetchedLightsEvent;
import com.giganticsheep.wifilight.api.FetchedLocationsEvent;
import com.giganticsheep.wifilight.api.GroupFetchedEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.LightFetchedEvent;
import com.giganticsheep.wifilight.api.LocationFetchedEvent;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import rx.Subscriber;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightControlTest extends ModelTest {

    @Test
    public void testFetchLightNetwork() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.fetchLightNetwork()
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Location location) {

                    }
                });
    }

    @Test
    public void testFetchLights() {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] lightsCount = new int[1];
        lightControl.fetchLights(true)
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() {
                        int totalLightCount = 0;
                        for(int i = 0; i < testLightNetwork.groupCount(); i++) {
                            totalLightCount += testLightNetwork.lightCount(i);
                        }

                        assertThat(lightsCount[0], equalTo(totalLightCount));

                        FetchedLightsEvent fetchedLightsEvent = getCheckedEvent(FetchedLightsEvent.class);
                        assertThat(fetchedLightsEvent.getLightsFetchedCount(), equalTo(lightsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Light light) {
                        lightsCount[0]++;

                        assertTrue(testLightNetwork.lightExists(light.getGroup().getId(), light.getId()));

                        LightFetchedEvent lightFetchedEvent = getCheckedEvent(LightFetchedEvent.class);
                        assertThat(lightFetchedEvent.getLight(), equalTo(light));
                    }
                });
    }

    @Test
    public void testFetchLocations() {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] locationsCount = new int[1];
        lightControl.fetchLocations(true)
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        FetchedLocationsEvent fetchedLocationsEvent = getCheckedEvent(FetchedLocationsEvent.class);
                        assertThat(fetchedLocationsEvent.getLocationsFetchedCount(), equalTo(locationsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Location location) {
                        locationsCount[0]++;

                        LocationFetchedEvent locationFetchedEvent = getCheckedEvent(LocationFetchedEvent.class);
                        assertThat(locationFetchedEvent.getLocation(), equalTo(location));
                    }
                });
    }

    @Test
    public void testFetchGroups() {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] groupsCount = new int[1];
        lightControl.fetchGroups(true)
                .subscribe(new Subscriber<Group>() {
                    @Override
                    public void onCompleted() {
                        assertThat(groupsCount[0], equalTo(testLightNetwork.groupCount()));

                        FetchedGroupsEvent fetchedGroupsEvent = getCheckedEvent(FetchedGroupsEvent.class);
                        assertThat(fetchedGroupsEvent.getGroupsFetchedCount(), equalTo(groupsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Group group) {
                        groupsCount[0]++;

                        assertTrue(testLightNetwork.groupExists(group.getId()));

                        GroupFetchedEvent groupFetchedEvent = getCheckedEvent(GroupFetchedEvent.class);
                        assertThat(groupFetchedEvent.getGroup(), equalTo(group));
                    }
                });
    }

    @Test
    public void testFetchLight() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.fetchLight(Constants.TEST_ID)
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(@NonNull Light light) {
                        assertThat(light.getId(), equalTo(Constants.TEST_ID));
                    }
                });
    }

    @Test
    public void testSetHue() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.setHue(300, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }

    @Test
    public void testSetSaturation() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.setSaturation(100, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }

    @Test
    public void testSetBrightness() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.setBrightness(100, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }

    @Test
    public void testSetKelvin() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.setKelvin(3000, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }

    @Test
    public void testToggleLights() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.togglePower()
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }

    @Test
    public void testSetPower() {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightControl.setPower(LightControl.Power.ON, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<LightStatus>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(LightStatus statusResponse) { }
                });
    }
}