package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.google.gson.annotations.SerializedName;

import org.apache.http.HttpException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetwork {

    @SuppressWarnings("FieldNotUsedInToString")
    protected static final Logger logger = new Logger("LightNetwork");

    // TODO groups
    // TODO locations
    // TODO selectors

    protected RestAdapter restAdapter;
    private final LightService lightService;

    private final List<Light> lights = new ArrayList<>();
    private final String apiKey;

    /**
     * @param apiKey
     */
    public LightNetwork(final String apiKey) {
        this.apiKey = apiKey;

        lightService = createLightService();

        fetchLights().subscribe();
    }

    protected LightService createLightService() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(WifiLightApplication.application().serverURL())
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError cause) {
                        logger.error(cause.getResponse().getReason());
                        return new Exception(cause.getMessage());
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter.create(LightService.class);
    }

    /**
     * @param hue the hue to set the enabled lights
     */
    public final Observable setHue(final int hue, float duration) {
        return doSetColour(makeHueString(hue), makeDurationString(duration));
    }

    /**
     * @param saturation the saturation to set the enabled lights
     */
    public final Observable setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationString(saturation), makeDurationString(duration));
    }

    /**
     * @param brightness the brightness to set the enabled lights
     */
    public final Observable setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessString(brightness), makeDurationString(duration));
    }

    /**
     * @param kelvin the kelvin (warmth to set the enabled lights
     */
    public final Observable setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinString(kelvin), makeDurationString(duration));
    }

    /**
     * toggles the power of the enabled lights
     *
     */
    public final Observable toggleLights() {
        return doToggleLights();
    }

    /**
     * @param power ON or OFF
     * @param duration how long to set the power change for
     */
    public final Observable setPower(final ModelConstants.Power power, final float duration) {
        return doSetPower(power.powerString(), makeDurationString(duration));
    }

    // TODO colour set power on

    final Observable<List<Light>> fetchLights() {
        lights.clear();

        return service().listLights(baseUrl1(), baseUrl2(), ModelConstants.URL_ALL, authorisation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        logger.error(throwable);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        WifiLightApplication.application().postMessage(new SuccessEvent());
                    }
                })
                .flatMap(new Func1<List<LightDataResponse>, Observable<List<Light>>>() {
                             @Override
                             public Observable<List<Light>> call(List<LightDataResponse> lightDataResponses) {
                                 List<Light> lights = new ArrayList();
                                 for (LightDataResponse lightDataResponse : lightDataResponses) {
                                     if (lightDataResponse.httpCode != 200) {
                                         return Observable.error(
                                                 new HttpException("Problem fetching light data"));
                                     }

                                     lights.add(new Light(LightNetwork.this, true, lightDataResponse));
                                 }

                                 return Observable.just(lights);
                             }
                         }
                );
    }

    /**
     * @param colourQuery the colour query
     * @param durationQuery the colour query
     */
    private Observable doSetColour(final String colourQuery, final String durationQuery) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(ModelConstants.URL_COLOUR, colourQuery);
        queryMap.put(ModelConstants.URL_DURATION, durationQuery);
        queryMap.put(ModelConstants.URL_POWER_ON, "true");

        return service().setColour(baseUrl1(),
                baseUrl2(),
                ModelConstants.URL_ALL,
                authorisation(),
                queryMap)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable doToggleLights() {
        return service().togglePower(baseUrl1(),
                baseUrl2(),
                ModelConstants.URL_ALL,
                authorisation())
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable doSetPower(final String powerQuery, final String durationQuery) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(ModelConstants.URL_STATE, powerQuery);
        queryMap.put(ModelConstants.URL_DURATION, durationQuery);

        return service().setPower(baseUrl1(),
                baseUrl2(),
                ModelConstants.URL_ALL,
                authorisation(),
                queryMap)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private String authorisation() {
        return ModelConstants.LABEL_BEARER + ModelConstants.SPACE + apiKey;
    }

    private final LightService service() {
        return lightService;
    }

    private final String baseUrl1() {
        return WifiLightApplication.application().baseURL1();
    }

    private final String baseUrl2() {
        return WifiLightApplication.application().baseURL2();
    }

    private String makeHueString(final double hue) {
        return ModelConstants.LABEL_HUE + Double.toString(hue);
    }

    private String makeSaturationString(final double saturation) {
        return ModelConstants.LABEL_SATURATION + Double.toString(saturation);
    }

    private String makeKelvinString(final long kelvin) {
        return ModelConstants.LABEL_KELVIN + Long.toString(kelvin);
    }

    private String makeBrightnessString(final double brightness) {
        return ModelConstants.LABEL_BRIGHTNESS + Double.toString(brightness);
    }

    private String makeDurationString(final float duration) {
        return Float.toString(duration);
    }

    @Override
    public String toString() {
        return "LightNetwork{" +
                "lights=" + lights +
                '}';
    }

    private class LightResponse {
        @SerializedName("cod")
        public int httpCode;

        public String id;
        public String label;
    }

    class StatusResponse extends LightResponse {
        public String status;
    }

    class LightDataResponse extends LightResponse {
        public String uuid;
        public boolean connected;
        public String power;
        public double brightness;
        public String product_name;
        public String last_seen;
        public double seconds_since_last_seen;

        public ColorData color;
        public GroupData location;
        public GroupData group;
        public CapabilitiesData capabilities;

        public class ColorData {
            public Double hue;
            public Double saturation;
            public Long kelvin;
        }

        public class GroupData {
            public String id;
            public String name;
        }

        public class CapabilitiesData {
            public boolean has_color;
            public boolean has_variable_color_temp;
        }
    }

    public class SuccessEvent { }

    /**
     * Created by anne on 22/06/15.
     * (*_*)
     */
    interface LightService {

        @GET("/{url1}/{url2}/{selector}")
        Observable<List<LightDataResponse>> listLights(@Path("url1") String url1, @Path("url2") String url2, @Path("selector") String selector,
                                                                       @Header("Authorization") String authorisation);

        @GET("/{url}/{url2}/{selector}/toggle")
        Observable<StatusResponse> togglePower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                         @Header("Authorization") String authorisation);

        @POST("/{url}/{url2}/{selector}/power")
        Observable<StatusResponse> setPower(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                      @Header("Authorization") String authorization,
                                      @QueryMap Map<String, String> options);

        @PUT("/{url}/{url2}/{selector}/color")
        Observable<StatusResponse> setColour(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                       @Header("Authorization") String authorization,
                                       @QueryMap Map<String, String> options);

        @POST("/{url}/{url2}/{selector}/effects/breathe")
        Observable<StatusResponse> breathe(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                     @Header("Authorization") String authorization,
                                     @QueryMap Map<String, String> options);

        @POST("/{url}/{url2}/{selector}/effects/pulse")
        Observable<StatusResponse> pulse(@Path("url") String url, @Path("url2") String url2, @Path("selector") String selector,
                                   @Header("Authorization") String authorization,
                                   @QueryMap Map<String, String> options);

    }
}
