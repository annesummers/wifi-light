package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.util.Constants;

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

                LightResponse light = new LightResponse(Constants.TEST_ID);
                light.group.id = Constants.TEST_GROUP_ID;
                light.location.id = Constants.TEST_LOCATION_ID;
                lights.add(light);

                light = new LightResponse(Constants.TEST_ID2);
                light.group.id = Constants.TEST_GROUP_ID2;
                light.location.id = Constants.TEST_LOCATION_ID;
                lights.add(light);

                light = new LightResponse(Constants.TEST_ID3);
                light.group.id = Constants.TEST_GROUP_ID;
                light.location.id = Constants.TEST_LOCATION_ID2;
                lights.add(light);

                light = new LightResponse(Constants.TEST_ID4);
                light.group.id = Constants.TEST_GROUP_ID;
                light.location.id = Constants.TEST_LOCATION_ID;
                lights.add(light);

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

                StatusResponse response = new StatusResponse();
                response.id = Constants.TEST_ID;
                response.label = Constants.TEST_LABEL;
                response.status = LightControl.Status.OK.getStatusString();

                responses.add(response);

                subscriber.onNext(responses);
                subscriber.onCompleted();
            }
        });
    }
}
