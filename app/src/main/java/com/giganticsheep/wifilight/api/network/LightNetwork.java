package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.Logger;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.LightDataResponse;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.di.HasComponent;
import com.giganticsheep.wifilight.di.components.DaggerNetworkComponent;
import com.giganticsheep.wifilight.di.components.NetworkComponent;
import com.giganticsheep.wifilight.di.modules.NetworkModule;
import com.giganticsheep.wifilight.ui.rx.BaseLogger;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightNetwork implements HasComponent<NetworkComponent> {

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger;

    // TODO groups
    // TODO locations
    // TODO selectors

    private final NetworkDetails networkDetails;
    private final WifiLightApplication.EventBus eventBus;

    private NetworkComponent networkComponent;

    //@Inject LightService service;
    private LightService lightService;

    @Inject
    public LightNetwork(final NetworkDetails networkDetails,
                        final WifiLightApplication.EventBus eventBus,
                        final BaseLogger baseLogger) {
        this.networkDetails = networkDetails;
        this.eventBus = eventBus;

        logger = new Logger("LightNetwork", baseLogger);

        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(networkDetails.getServerURL()))
                .build();
        networkComponent.inject(this);

        lightService = new RestAdapter.Builder()
                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint(Endpoints.newFixedEndpoint(networkDetails.getServerURL()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new GsonBuilder().create()))
                .build().create(LightService.class);

        fetchLights().subscribe();
    }

    @Override
    public NetworkComponent getComponent() {
        return networkComponent;
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
        return doSetColour(makeSaturationString((double)saturation/100), makeDurationString(duration));
    }

    /**
     * @param brightness the brightness to set the enabled lights
     */
    public final Observable setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessString((double)brightness/100), makeDurationString(duration));
    }

    /**
     * @param kelvin the kelvin (warmth to set the enabled lights
     */
    public final Observable setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinString(kelvin+2500), makeDurationString(duration));
    }

    /**
     * toggles the power of the enabled lights
     *
     */
    public final Observable togglePower() {
        return doToggleLights();
    }

    /**
     * @param power ON or OFF
     * @param duration how long to set the power change for
     */
    public final Observable setPower(final ModelConstants.Power power, final float duration) {
        return doSetPower(power.powerString(), makeDurationString(duration));
    }

    public final Observable<LightDataResponse> fetchLights() {
        logger.debug("fetchLights()");

        return lightService.listLights(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        logger.error(throwable);
                    }
                })
                .flatMap(new Func1<List<LightDataResponse>, Observable<LightDataResponse>>() {
                    @Override
                    public Observable<LightDataResponse> call(List<LightDataResponse> dataResponses) {
                        List<Observable<LightDataResponse>> observables = new ArrayList<>(dataResponses.size());

                        for (LightDataResponse dataResponse : dataResponses) {
                            logger.debug(dataResponse.toString());
                            eventBus.postMessage(new LightDetailsEvent(dataResponse)).subscribe();
                        }

                        return Observable.merge(observables);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * @param colourQuery the colour query
     * @param durationQuery the colour query
     */
    private Observable doSetColour(final String colourQuery, final String durationQuery) {
        logger.debug("doSetColour() " + colourQuery + NetworkConstants.SPACE + durationQuery);

        // TODO colour set power on
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(NetworkConstants.URL_COLOUR, colourQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);
        queryMap.put(NetworkConstants.URL_POWER_ON, "true");

        return lightService.setColour(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(),
                queryMap)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        logger.error(throwable);
                    }
                })
                .flatMap(new Func1<List<StatusResponse>, Observable<StatusResponse>>() {
                    @Override
                    public Observable<StatusResponse> call(List<StatusResponse> statusResponses) {
                        List<Observable<StatusResponse>> observables = new ArrayList<>(statusResponses.size());

                        for (StatusResponse statusResponse : statusResponses) {
                            logger.debug(statusResponse.toString());
                        }

                        return Observable.merge(observables);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable doToggleLights() {
        logger.debug("doToggleLights()");

        return lightService.togglePower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        logger.error(throwable);
                    }
                })
                .flatMap(new Func1<List<StatusResponse>, Observable<StatusResponse>>() {
                    @Override
                    public Observable<StatusResponse> call(List<StatusResponse> statusResponses) {
                        List<Observable<StatusResponse>> observables = new ArrayList<>(statusResponses.size());

                        for (StatusResponse statusResponse : statusResponses) {
                            logger.debug(statusResponse.toString());
                        }

                        return Observable.merge(observables);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private Observable doSetPower(final String powerQuery, final String durationQuery) {
        logger.debug("doSetPower() " + powerQuery + NetworkConstants.SPACE + durationQuery);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(NetworkConstants.URL_STATE, powerQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);

        return lightService.setPower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(),
                queryMap)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(final Throwable throwable) {
                        logger.error(throwable);
                    }
                })
                .flatMap(new Func1<List<StatusResponse>, Observable<StatusResponse>>() {
                    @Override
                    public Observable<StatusResponse> call(List<StatusResponse> statusResponses) {
                        List<Observable<StatusResponse>> observables = new ArrayList<>(statusResponses.size());

                        for (StatusResponse statusResponse : statusResponses) {
                            logger.debug(statusResponse.toString());
                        }

                        return Observable.merge(observables);
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        fetchLights().subscribe();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    private String authorisation() {
        return NetworkConstants.LABEL_BEARER + NetworkConstants.SPACE + networkDetails.getApiKey();
    }

    private String makeHueString(final double hue) {
        return NetworkConstants.LABEL_HUE + Double.toString(hue);
    }

    private String makeSaturationString(final double saturation) {
        return NetworkConstants.LABEL_SATURATION + Double.toString(saturation);
    }

    private String makeKelvinString(final long kelvin) {
        return NetworkConstants.LABEL_KELVIN + Long.toString(kelvin);
    }

    private String makeBrightnessString(final double brightness) {
        return NetworkConstants.LABEL_BRIGHTNESS + Double.toString(brightness);
    }

    private String makeDurationString(final float duration) {
        return Float.toString(duration);
    }

    public class SuccessEvent { }

    public class LightDetailsEvent {
        private final LightDataResponse light;

        private LightDetailsEvent(LightDataResponse light) {
            this.light = light;
        }

        public final LightDataResponse light() {
            return light;
        }
    }

}
