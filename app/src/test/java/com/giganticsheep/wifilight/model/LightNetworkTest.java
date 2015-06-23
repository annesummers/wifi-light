package com.giganticsheep.wifilight.model;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetworkTest extends TestCase {
    private static final float DEFAULT_DURATION = 1.0F;
    private static final float OVERFLOW_DURATION = 2.0F;

    LightNetwork testNetwork;
    private Throwable mError = null;

    @Before
    public void setUp() throws Exception {
        testNetwork = new LightNetwork("c5e3c4b06448baa75d3a849b7cdb70930e4b95e9e7160a4415c49bf03ffa45f8");
    }

    @After
    public void tearDown() throws Exception {
        mError = null;
    }

    @Test
    public void testSetHue() throws Exception {
        testNetwork.setHue(300, DEFAULT_DURATION)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setError(throwable);
                        notify();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        notify();
                    }
                })
                .subscribe();

        synchronized (this) {
            wait();
            handleError();
        }

        testNetwork.fetchLights()
                .doOnNext(new Action1<List<Light>>() {
                    @Override
                    public void call(List<Light> lights) {
                        for(Light light : lights) {
                            assertThat(light.hue(), is((double)300));
                        }

                        notify();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        setError(throwable);
                        notify();
                    }
                })
                .subscribe();
    }

    @Test
    public void testSetHueTooBig() throws Exception {
        testNetwork.setHue(3000, DEFAULT_DURATION);
    }

    @Test
    public void testSetHueDurationTooBig() throws Exception {
        testNetwork.setHue(300, OVERFLOW_DURATION);
    }

    @Test
    public void testSetSaturation() throws Exception {
        testNetwork.setSaturation(100, DEFAULT_DURATION);
    }

    @Test
    public void testSetSaturationTooBig() throws Exception {
        testNetwork.setSaturation(1000, DEFAULT_DURATION);
    }

    @Test
    public void testSetSaturationDurationTooBig() throws Exception {
        testNetwork.setSaturation(100, OVERFLOW_DURATION);
    }

    @Test
    public void testSetBrightness() throws Exception {
        testNetwork.setBrightness(100, DEFAULT_DURATION);
    }

    @Test
    public void testSetBrightnessTooBig() throws Exception {
        testNetwork.setBrightness(1000, DEFAULT_DURATION);
    }

    @Test
    public void testSetBrightnessDurationTooBig() throws Exception {
        testNetwork.setBrightness(100, OVERFLOW_DURATION);
    }

    @Test
    public void testSetKelvin() throws Exception {
        testNetwork.setKelvin(3000, DEFAULT_DURATION);
    }

    @Test
    public void testSetKelvinTooBig() throws Exception {
        testNetwork.setKelvin(30000, DEFAULT_DURATION);
    }

    @Test
    public void testSetKelvinDurationTooBig() throws Exception {
        testNetwork.setKelvin(3000, OVERFLOW_DURATION);
    }

    @Test
    public void testToggleLights() throws Exception {
        testNetwork.toggleLights();
    }

    @Test
    public void testSetPower() throws Exception {
        testNetwork.setPower(ModelConstants.Power.ON, DEFAULT_DURATION);
    }

    @Test
    public void testSetPowerDurationTooBig() throws Exception {
        testNetwork.setPower(ModelConstants.Power.ON, OVERFLOW_DURATION);
    }

    private void handleError() throws Exception {
        if(mError != null) {
            throw new Exception(mError);
        }
    }

    private void setError(Throwable e) {
        mError = e;
    }
}