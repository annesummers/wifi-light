package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightNetwork;
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

    private LightNetwork mockLightNetwork;

    public MockLightService(LightNetwork lightNetwork) {
        mockLightNetwork = lightNetwork;
    }

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
                LightResponse light;

                for(int i = 0; i < mockLightNetwork.lightLocationCount(); i++){
                    for (int j = 0; j < mockLightNetwork.lightGroupCount(i); j++) {
                        for (int k = 0; k < mockLightNetwork.lightCount(i, j); k++) {
                            light = new LightResponse(mockLightNetwork.getLight(i, j, k).getId());
                            light.group.id = mockLightNetwork.getLightGroup(i, j).getId();
                            light.location.id = mockLightNetwork.getLightLocation(i).getId();
                            lights.add(light);
                        }
                    }
                }

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
        return statusesObservable();
    }

    @NonNull
    @Override
    public Observable<StatusResponse> togglePowerSingleLight(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorisation, @Body Object empty) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> setPower (@Path("url") String url,
                                                    @Path("url2") String url2,
                                                    @Path("selector") String selector,
                                                    @Header("Authorization") String authorization,
                                                    @FieldMap Map < String, String > options){
        return statusesObservable();
    }

    @NonNull
    @Override
    public Observable<StatusResponse> setPowerSingleLight(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> setColour (@Path("url") String url,
                                                        @Path("url2") String url2,
                                                        @Path("selector") String selector,
                                                        @Header("Authorization") String authorization,
                                                        @FieldMap Map < String, String > options){
        return statusesObservable();
    }

    @NonNull
    @Override
    public Observable<StatusResponse> setColourSingleLight(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> breathe (@Path("url") String url,
                                                    @Path("url2") String url2,
                                                    @Path("selector") String selector,
                                                    @Header("Authorization") String authorization,
                                                    @FieldMap Map < String, String > options){
        return statusesObservable();
    }

    @NonNull
    @Override
    public Observable<StatusResponse> breatheSingleLight(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    @NonNull
    @Override
    public Observable<List<StatusResponse>> pulse (@Path("url") String url,
                                                    @Path("url2") String url2,
                                                    @Path("selector") String selector,
                                                    @Header("Authorization") String authorization,
                                                    @FieldMap Map < String, String > options){
        return statusesObservable();
    }

    @NonNull
    @Override
    public Observable<StatusResponse> pulseSingleLight(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @FieldMap Map<String, String> options) {
        return statusObservable();
    }

    private Observable<List<StatusResponse>> statusesObservable() {
        return Observable.create(new Observable.OnSubscribe<List<StatusResponse>>() {
            @Override
            public void call(Subscriber<? super List<StatusResponse>> subscriber) {
                List<StatusResponse> responses = new ArrayList<>();
                StatusResponse response;

                for (int i = 0; i < mockLightNetwork.lightLocationCount(); i++) {
                    for (int j = 0; j < mockLightNetwork.lightGroupCount(i); j++) {
                        for (int k = 0; k < mockLightNetwork.lightCount(i, j); k++) {
                            response = new StatusResponse();
                            Light light = mockLightNetwork.getLight(i, j, k);
                            response.id = light.getId();
                            response.label = light.getLabel();
                            response.status = LightControl.Status.OK.getStatusString();

                            responses.add(response);
                        }
                    }
                }

                subscriber.onNext(responses);
                subscriber.onCompleted();
            }
        });
    }

    private Observable<StatusResponse> statusObservable() {
        return Observable.create(new Observable.OnSubscribe<StatusResponse>() {
            @Override
            public void call(Subscriber<? super StatusResponse> subscriber) {
                StatusResponse response = new StatusResponse();
                response.id = Constants.TEST_ID;
                response.label = Constants.TEST_LABEL;
                response.status = LightControl.Status.OK.getStatusString();

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }
}
