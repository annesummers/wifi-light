package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.AndroidEventBus;
import com.giganticsheep.wifilight.TestEventBus;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.WifiLightTest;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.api.network.LightService;
import com.giganticsheep.wifilight.api.network.NetworkDetails;

import org.jetbrains.annotations.NonNls;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.MockRestAdapter;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetworkTest extends WifiLightTest {
    private static final float DEFAULT_DURATION = 1.0F;

    static final String TEST_ID = "12345abcde";
    static final String TEST_ID2 = "absde12345";

    private LightNetwork testNetwork;

    private final Object lightSyncObject = new Object();

    @Before
    public void setUp() throws Exception {
        NetworkDetails networkDetails = new NetworkDetails(TestLightNetwork.DEFAULT_API_KEY,
                TestLightNetwork.DEFAULT_SERVER_STRING,
                TestLightNetwork.DEFAULT_URL_STRING1,
                TestLightNetwork.DEFAULT_URL_STRING2);

        testNetwork = new TestLightNetwork(networkDetails,
                new TestEventBus(),
                baseLogger,
                this);

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @After
    public void tearDown() throws Exception {
        reset();
    }

    @Test
    public void testFetchLights() throws Exception {
        testNetwork.fetchLights(true)
                .subscribeOn(Schedulers.immediate())
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
        testNetwork.fetchLight(TEST_ID)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(Light light) {
                        assertThat(light.id(), equalTo(TEST_ID));
                    }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());

    }

    @Test
    public void testSetHue() throws Exception {
        testNetwork.setHue(300, DEFAULT_DURATION)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @Test
    public void testSetSaturation() throws Exception {
        testNetwork.setSaturation(100, DEFAULT_DURATION)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @Test
    public void testSetBrightness() throws Exception {
        testNetwork.setBrightness(100, DEFAULT_DURATION)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @Test
    public void testSetKelvin() throws Exception {
        testNetwork.setKelvin(3000, DEFAULT_DURATION)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @Test
    public void testToggleLights() throws Exception {
        testNetwork.togglePower()
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

    @Test
    public void testSetPower() throws Exception {
        testNetwork.setPower(ModelConstants.Power.ON, DEFAULT_DURATION)
                .subscribeOn(Schedulers.immediate())
                .subscribe(new Subscriber<StatusResponse>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) { }

                    @Override
                    public void onNext(StatusResponse statusResponse) { }
                });

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(!isSignalled());
    }

}