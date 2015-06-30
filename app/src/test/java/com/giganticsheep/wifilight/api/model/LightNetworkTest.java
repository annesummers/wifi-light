package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.TestEventBus;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.WifiLightTest;
import com.giganticsheep.wifilight.base.BaseLogger;
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

import retrofit.ErrorHandler;
import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetworkTest extends WifiLightTest {
    private static final float DEFAULT_DURATION = 1.0F;

    @NonNls private static final String DEFAULT_API_KEY = "c5e3c4b06448baa75d3a849b7cdb70930e4b95e9e7160a4415c49bf03ffa45f8";
    @NonNls private static final String DEFAULT_SERVER_STRING = "https://api.lifx.com";
    @NonNls private static final String DEFAULT_URL_STRING1 = "v1beta1";
    @NonNls private static final String DEFAULT_URL_STRING2 = "lights";

    private static final String TEST_ID = "12345abcde";
    private static final String TEST_ID2 = "absde12345";

    private MockRestAdapter mockRestAdapter;
    private LightNetwork testNetwork;

    private Light light = null;

    private final Object lightSyncObject = new Object();

    @Before
    public void setUp() throws Exception {
        NetworkDetails networkDetails = new NetworkDetails(DEFAULT_API_KEY,
                DEFAULT_SERVER_STRING,
                DEFAULT_URL_STRING1,
                DEFAULT_URL_STRING2);
        testNetwork = new TestLightNetwork(networkDetails,
                new TestEventBus(),
                baseLogger);

        do {
            synchronized (lightSyncObject) {
                try {
                    lightSyncObject.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while(light == null);
    }

    @After
    public void tearDown() throws Exception {
        light = null;
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
                        assertThat(light.id(),  equalTo(TEST_ID));
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
        } while(light == null);

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
        } while(light == null);
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
        } while(light == null);
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
        } while(light == null);
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
        } while(light == null);
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
        } while(light == null);
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
        } while(light == null);
    }

    private class TestLightNetwork extends LightNetwork {
        /**
         * @param networkDetails
         */
        public TestLightNetwork(NetworkDetails networkDetails,
                                TestEventBus eventBus,
                                BaseLogger baseLogger) {
            super(networkDetails, eventBus, baseLogger);
        }

        @Override
        protected LightService createLightService() {
            mockRestAdapter = MockRestAdapter.from(createRestAdapter());
            mockRestAdapter.setErrorPercentage(20);
            return mockRestAdapter.create(LightService.class, new MockLightService());
        }

        @Override
        protected RestAdapter createRestAdapter() {
            return new RestAdapter.Builder()
                    .setEndpoint("mock:///")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setErrorHandler(new ErrorHandler() {
                        @Override
                        public Throwable handleError(RetrofitError cause) {
                            LightNetworkTest.this.light = new Light(TEST_ID);

                            return new Exception();
                        }
                    })
                    .build();
        }
    }

    private class MockLightService implements LightService {
        @Override
        public Observable<List<Light>> listLights(@Path("url1") String url1,
                                                  @Path("url2") String url2,
                                                  @Path("selector") String selector,
                                                  @Header("Authorization") String authorisation) {
            return Observable.create(new Observable.OnSubscribe<List<Light>>() {
                @Override
                public void call(Subscriber<? super List<Light>> subscriber) {
                    List<Light> lights = new ArrayList<>();
                    Light light = new Light(TEST_ID);
                    lights.add(light);
                    lights.add(new Light(TEST_ID2));

                    subscriber.onNext(lights);
                    subscriber.onCompleted();

                    synchronized (LightNetworkTest.this.lightSyncObject) {
                        LightNetworkTest.this.light = light;
                    }
                }
            });
        }

        private Observable<List<StatusResponse>> statusObservable() {
            return Observable.create(new Observable.OnSubscribe<List<StatusResponse>>() {
                @Override
                public void call(Subscriber<? super List<StatusResponse>> subscriber) {
                    List<StatusResponse> responses = new ArrayList<>();
                    responses.add(new StatusResponse());

                    subscriber.onNext(responses);
                    subscriber.onCompleted();
                }
            });
        }

        @Override
        public Observable<List<StatusResponse>> togglePower(@Path("url") String url,
                                                            @Path("url2") String url2,
                                                            @Path("selector") String selector,
                                                            @Header("Authorization") String authorisation,
                                                            @Body Object empty) {
            return statusObservable();
        }

        @Override
        public Observable<List<StatusResponse>> setPower(@Path("url") String url,
                                                         @Path("url2") String url2,
                                                         @Path("selector") String selector,
                                                         @Header("Authorization") String authorization,
                                                         @FieldMap Map<String, String> options) {
            return statusObservable();
        }

        @Override
        public Observable<List<StatusResponse>> setColour(@Path("url") String url,
                                                          @Path("url2") String url2,
                                                          @Path("selector") String selector,
                                                          @Header("Authorization") String authorization,
                                                          @FieldMap Map<String, String> options) {
            return statusObservable();
        }

        @Override
        public Observable<List<StatusResponse>> breathe(@Path("url") String url,
                                                        @Path("url2") String url2,
                                                        @Path("selector") String selector,
                                                        @Header("Authorization") String authorization,
                                                        @FieldMap Map<String, String> options) {
            return statusObservable();
        }

        @Override
        public Observable<List<StatusResponse>> pulse(@Path("url") String url,
                                                      @Path("url2") String url2,
                                                      @Path("selector") String selector,
                                                      @Header("Authorization") String authorization,
                                                      @FieldMap Map<String, String> options) {
            return statusObservable();
        }
    }

}