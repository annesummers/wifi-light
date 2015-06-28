package com.giganticsheep.wifilight.di.modules;

import com.giganticsheep.wifilight.api.model.LightDataResponse;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.api.network.LightService;

import java.util.List;
import java.util.Map;

import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by anne on 28/06/15.
 * (*_*)
 */
public class LightServiceImpl implements LightService {
    @Override
    public Observable<List<LightDataResponse>> listLights(@Path("url1") String url1, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorisation) {
        return null;
    }

    @Override
    public Observable<List<StatusResponse>> togglePower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorisation) {
        return null;
    }

    @Override
    public Observable<List<StatusResponse>> setPower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @QueryMap Map<String, String> options) {
        return null;
    }

    @Override
    public Observable<List<StatusResponse>> setColour(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @QueryMap Map<String, String> options) {
        return null;
    }

    @Override
    public Observable<List<StatusResponse>> breathe(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @QueryMap Map<String, String> options) {
        return null;
    }

    @Override
    public Observable<List<StatusResponse>> pulse(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector, @Header("Authorization") String authorization, @QueryMap Map<String, String> options) {
        return null;
    }
}
