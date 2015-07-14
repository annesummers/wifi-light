package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.StatusResponse;
import com.giganticsheep.wifilight.base.TestConstants;

import org.junit.Test;

import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetworkTest extends ModelTest {

    @Test
    public void testFetchLights() throws Exception {
        lightNetwork.fetchLights(true)
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
        lightNetwork.fetchLight(TestConstants.TEST_ID)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(@NonNull Light light) {
                        assertThat(light.id(), equalTo(TestConstants.TEST_ID));
                    }
                });
    }

    @Test
    public void testSetHue() throws Exception {
        lightNetwork.setHue(300, TestConstants.TEST_DURATION)
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
        lightNetwork.setSaturation(100, TestConstants.TEST_DURATION)
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
        lightNetwork.setBrightness(100, TestConstants.TEST_DURATION)
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
        lightNetwork.setKelvin(3000, TestConstants.TEST_DURATION)
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
        lightNetwork.togglePower()
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
        lightNetwork.setPower(LightControl.Power.ON, TestConstants.TEST_DURATION)
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