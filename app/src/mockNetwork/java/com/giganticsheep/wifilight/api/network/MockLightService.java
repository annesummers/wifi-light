package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

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

    @NonNull
    @Override
    public Observable<List<LightResponse>> listLights(@Path("url1") String url1,
                                              @Path("url2") String url2,
                                              @Path("selector") String selector,
                                              @Header("Authorization") String authorisation) {
        return Observable.create(new Observable.OnSubscribe<List<LightResponse>>() {
            @Override
            public void call(Subscriber<? super List<LightResponse>> subscriber) {
                List<LightResponse> lights = new ArrayList<>();
                LightResponse light = new LightResponse(NetworkConstants.TEST_ID);
                lights.add(light);
                lights.add(new LightResponse(NetworkConstants.TEST_ID2));

                subscriber.onNext(lights);
                subscriber.onCompleted();
            }
        });
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> togglePower(@Path("url") String url,
                                                        @Path("url2") String url2,
                                                        @Path("selector") String selector,
                                                        @Header("Authorization") String authorisation,
                                                        @Body Object empty) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> setPower(@Path("url") String url,
                                                     @Path("url2") String url2,
                                                     @Path("selector") String selector,
                                                     @Header("Authorization") String authorization,
                                                     @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> setColour(@Path("url") String url,
                                                      @Path("url2") String url2,
                                                      @Path("selector") String selector,
                                                      @Header("Authorization") String authorization,
                                                      @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> breathe(@Path("url") String url,
                                                    @Path("url2") String url2,
                                                    @Path("selector") String selector,
                                                    @Header("Authorization") String authorization,
                                                    @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> pulse(@Path("url") String url,
                                                  @Path("url2") String url2,
                                                  @Path("selector") String selector,
                                                  @Header("Authorization") String authorization,
                                                  @FieldMap Map<String, String> options) {
        return statusObservable();
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
}
