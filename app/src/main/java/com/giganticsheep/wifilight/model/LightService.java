package com.giganticsheep.wifilight.model;

import android.os.Handler;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by anne on 22/06/15.
 */
public interface LightService {

    @PUT("{url}")
    Observable<Response> setColour(@Path("url") String path, @Header("Authorization") String authorization, @Query("color") String colour);

    @GET("{url}")
    Observable<Response> listLights(@Path("url") String path, @Header("Authorization") String authorisation);

    //n, @Path("light_name") String lightName
}
