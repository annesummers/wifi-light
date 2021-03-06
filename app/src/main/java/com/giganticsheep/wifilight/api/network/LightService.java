package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public interface LightService {

    @NonNull
    @GET("/{url1}/{url2}/{selector}")
    Observable<List<LightResponse>> listLights(@Path("url1") String url1,
                                                   @Path("url2") String url2,
                                                   @Path("selector") String selector,
                                                   @Header("Authorization") String authorisation);

    @NonNull
    @POST("/{url}/{url2}/{selector}/toggle")
    Observable<List<StatusResponse>> togglePower(@Path("url") String url,
                                                 @Path("url2") String url2,
                                                 @Path("selector") String selector,
                                                 @Header("Authorization") String authorisation,
                                                 @Body Object empty);

    @NonNull
    @POST("/{url}/{url2}/{selector}/toggle")
    Observable<StatusResponse> togglePowerSingleLight(@Path("url") String url,
                                                             @Path("url2") String url2,
                                                             @Path("selector") String selector,
                                                             @Header("Authorization") String authorisation,
                                                             @Body Object empty);
    @NonNull
    @FormUrlEncoded
    @PUT("/{url}/{url2}/{selector}/power")
    Observable<List<StatusResponse>> setPower(@Path("url") String url,
                                              @Path("url2") String url2,
                                              @Path("selector") String selector,
                                              @Header("Authorization") String authorization,
                                              @FieldMap Map<String, String> options);
    @NonNull
    @FormUrlEncoded
    @PUT("/{url}/{url2}/{selector}/power")
    Observable<StatusResponse> setPowerSingleLight(@Path("url") String url,
                                              @Path("url2") String url2,
                                              @Path("selector") String selector,
                                              @Header("Authorization") String authorization,
                                              @FieldMap Map<String, String> options);

    @NonNull
    @FormUrlEncoded
    @PUT("/{url}/{url2}/{selector}/color")
    Observable<List<StatusResponse>> setColour(@Path("url") String url,
                                               @Path("url2") String url2,
                                               @Path("selector") String selector,
                                               @Header("Authorization") String authorization,
                                               @FieldMap Map<String, String> options);

    @NonNull
    @FormUrlEncoded
    @PUT("/{url}/{url2}/{selector}/color")
    Observable<StatusResponse> setColourSingleLight(@Path("url") String url,
                                                    @Path("url2") String url2,
                                                    @Path("selector") String selector,
                                                    @Header("Authorization") String authorization,
                                                    @FieldMap Map<String, String> options);

    @NonNull
    @FormUrlEncoded
    @POST("/{url}/{url2}/{selector}/effects/breathe")
    Observable<List<StatusResponse>> breathe(@Path("url") String url,
                                             @Path("url2") String url2,
                                             @Path("selector") String selector,
                                             @Header("Authorization") String authorization,
                                             @FieldMap Map<String, String> options);
    @NonNull
    @FormUrlEncoded
    @POST("/{url}/{url2}/{selector}/effects/breathe")
    Observable<StatusResponse> breatheSingleLight(@Path("url") String url,
                                             @Path("url2") String url2,
                                             @Path("selector") String selector,
                                             @Header("Authorization") String authorization,
                                             @FieldMap Map<String, String> options);

    @NonNull
    @FormUrlEncoded
    @POST("/{url}/{url2}/{selector}/effects/pulse")
    Observable<List<StatusResponse>> pulse(@Path("url") String url,
                                           @Path("url2") String url2,
                                           @Path("selector") String selector,
                                           @Header("Authorization") String authorization,
                                           @FieldMap Map<String, String> options);
    @NonNull
    @FormUrlEncoded
    @POST("/{url}/{url2}/{selector}/effects/pulse")
    Observable<StatusResponse> pulseSingleLight(@Path("url") String url,
                                           @Path("url2") String url2,
                                           @Path("selector") String selector,
                                           @Header("Authorization") String authorization,
                                           @FieldMap Map<String, String> options);
}
