package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.WifiLightTest;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.util.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetworkTest extends WifiLightTest {

    @Inject LightNetwork testNetwork;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        reset();
    }

    @Test
    public void testFetchLights() throws Exception {
        testNetwork.fetchLights(true)
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Light light) {
                    }
                });
    }

    @Test
    public void testFetchLight() throws Exception {
        testNetwork.fetchLight(TestConstants.TEST_ID)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Light light) {
                        assertThat(light.id(), equalTo(TestConstants.TEST_ID));
                    }
                });
    }

    @Test
    public void testSetHue() throws Exception {
        testNetwork.setHue(300, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }

    @Test
    public void testSetSaturation() throws Exception {
        testNetwork.setSaturation(100, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }

    @Test
    public void testSetBrightness() throws Exception {
        testNetwork.setBrightness(100, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }

    @Test
    public void testSetKelvin() throws Exception {
        testNetwork.setKelvin(3000, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }

    @Test
    public void testToggleLights() throws Exception {
        testNetwork.togglePower()
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }

    @Test
    public void testSetPower() throws Exception {
        testNetwork.setPower(ModelConstants.Power.ON, TestConstants.TEST_DURATION)
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });
    }
}