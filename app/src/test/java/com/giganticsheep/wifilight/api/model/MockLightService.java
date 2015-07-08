package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.WifiLightTest;
import com.giganticsheep.wifilight.api.network.LightService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.Header;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by anne on 30/06/15.
 * (*_*)
 */
class MockLightService implements LightService {

    private final WifiLightTest test;

    MockLightService(WifiLightTest test) {
        this.test = test;
    }

    @Override
    public Observable<List<Light>> listLights(@Path("url1") String url1,
                                              @Path("url2") String url2,
                                              @Path("selector") String selector,
                                              @Header("Authorization") String authorisation) {
        return Observable.create(new Observable.OnSubscribe<List<Light>>() {
            @Override
            public void call(Subscriber<? super List<Light>> subscriber) {
                List<Light> lights = new ArrayList<>();
                Light light = new Light(LightNetworkTest.TEST_ID);
                lights.add(light);
                lights.add(new Light(LightNetworkTest.TEST_ID2));

                test.signal();

                subscriber.onNext(lights);
                subscriber.onCompleted();
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
