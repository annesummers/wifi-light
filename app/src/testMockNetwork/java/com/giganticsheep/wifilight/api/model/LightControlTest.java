package com.giganticsheep.wifilight.api.model;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.network.error.WifiLightException;
import com.giganticsheep.wifilight.base.TestConstants;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import rx.Subscriber;

import static org.hamcrest.CoreMatchers.instanceOf;
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
                .subscribe(new Subscriber<LightNetwork>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightNetwork lightNetwork) {
                        assertThat(lightNetwork, equalTo(testLightNetwork));
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
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
                    public void onError(Throwable e) {
                        assertThat(e, instanceOf(WifiLightException.class));
                    }

                    @Override
                    public void onNext(LightStatus statusResponse) {
                        assertThat(statusResponse.getId(), equalTo(Constants.TEST_ID));
                    }
                });
    }
}