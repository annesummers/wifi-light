package com.giganticsheep.wifilight.api.network;

import java.util.List;
import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
public interface LightService {

    @GET("/{url1}/{url2}/{selector}")
    Observable<List<LightDataResponse>> listLights(@Path("url1") String url1, @Path("url2") String url2, @Path("selector") String selector,
                                                                @Header("Authorization") String authorisation);

    @POST("/{url}/{url2}/{selector}/toggle")
    Observable<List<StatusResponse>> togglePower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                                              @Header("Authorization") String authorisation);

    @PUT("/{url}/{url2}/{selector}/power")
    Observable<List<StatusResponse>> setPower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                                           @Header("Authorization") String authorization,
                                                           @QueryMap Map<String, String> options);

    @PUT("/{url}/{url2}/{selector}/color")
    Observable<List<StatusResponse>> setColour(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                                            @Header("Authorization") String authorization,
                                                            @QueryMap Map<String, String> options);

    @POST("/{url}/{url2}/{selector}/effects/breathe")
    Observable<List<StatusResponse>> breathe(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                                          @Header("Authorization") String authorization,
                                                          @QueryMap Map<String, String> options);

    @POST("/{url}/{url2}/{selector}/effects/pulse")
    Observable<List<StatusResponse>> pulse(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                                        @Header("Authorization") String authorization,
                                                        @QueryMap Map<String, String> options);

}
