package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.FetchGroupsEvent;
import com.giganticsheep.wifilight.api.FetchLightsEvent;
import com.giganticsheep.wifilight.api.FetchLocationsEvent;
import com.giganticsheep.wifilight.api.FetchedGroupEvent;
import com.giganticsheep.wifilight.api.FetchedLightEvent;
import com.giganticsheep.wifilight.api.FetchedLocationEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.TestEventBus;

import org.junit.Test;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightControlTest extends ModelTest {

    @Test
    public void testFetchLights() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] lightsCount = new int[1];
        lightNetwork.fetchLights(true)
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() {
                        Object lightChangedEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(lightChangedEvent, instanceOf(FetchLightsEvent.class));
                        assertThat(((FetchLightsEvent)lightChangedEvent).getLightsFetchedCount(), equalTo(lightsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Light light) {
                        lightsCount[0]++;

                        Object fetchLightEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(fetchLightEvent, instanceOf(FetchedLightEvent.class));
                        assertThat(((FetchedLightEvent) fetchLightEvent).getLight(), equalTo(light));
                    }
                });
    }

    @Test
    public void testFetchLocations() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] locationsCount = new int[1];
        lightNetwork.fetchLocations(true)
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                        Object fetchLocationEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(fetchLocationEvent, instanceOf(FetchLocationsEvent.class));
                        assertThat(((FetchLocationsEvent)fetchLocationEvent).getLocationsFetchedCount(), equalTo(locationsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Location location) {
                        locationsCount[0]++;

                        Object fetchLocationEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(fetchLocationEvent, instanceOf(FetchedLocationEvent.class));
                        assertThat(((FetchedLocationEvent)fetchLocationEvent).getLocation(), equalTo(location));
                    }
                });
    }

    @Test
    public void testFetchGroups() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        int[] groupsCount = new int[1];
        lightNetwork.fetchGroups(true)
                .subscribe(new Subscriber<Group>() {
                    @Override
                    public void onCompleted() {
                        Object fetchGroupEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(fetchGroupEvent, instanceOf(FetchGroupsEvent.class));
                        assertThat(((FetchGroupsEvent)fetchGroupEvent).getGroupsFetchedCount(), equalTo(groupsCount[0]));
                    }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Group group) {
                        groupsCount[0]++;

                        Object fetchGroupEvent = ((TestEventBus)eventBus).popLastMessage();
                        assertThat(fetchGroupEvent, instanceOf(FetchedGroupEvent.class));
                        assertThat(((FetchedGroupEvent)fetchGroupEvent).getGroup(), equalTo(group));
                    }
                });
    }

    @Test
    public void testFetchLight() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.fetchLight(Constants.TEST_ID)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(@NonNull Light light) {
                        assertThat(light.id(), equalTo(Constants.TEST_ID));
                    }
                });
    }

    @Test
    public void testSetHue() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.setHue(300, TestConstants.TEST_DURATION)
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
    public void testSetSaturation() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.setSaturation(100, TestConstants.TEST_DURATION)
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
    public void testSetBrightness() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.setBrightness(100, TestConstants.TEST_DURATION)
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
    public void testSetKelvin() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.setKelvin(3000, TestConstants.TEST_DURATION)
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
    public void testToggleLights() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.togglePower()
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
    public void testSetPower() throws Exception {
        if(BuildConfig.DEBUG) {
            return;
        }

        lightNetwork.setPower(LightControl.Power.ON, TestConstants.TEST_DURATION)
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