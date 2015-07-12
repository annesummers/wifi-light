package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.StatusResponse;
import com.giganticsheep.wifilight.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.dagger.UIScheduler;
import com.giganticsheep.wifilight.ui.dagger.ActivityScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */

@ActivityScope
public class LightNetwork {

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger;

    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Light> lightsObservable;

    @SuppressWarnings("FieldNotUsedInToString")
    private final Subscriber errorSubscriber;

    // TODO groups
    // TODO locations
    // TODO selectors
    // TODO effects
    // TODO javadoc
    // TODO connected can we find the light?

    private final NetworkDetails networkDetails;
    private final EventBus eventBus;

    private final LightService lightService;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    @Inject
    public LightNetwork(final NetworkDetails networkDetails,
                        final LightService lightService,
                        final EventBus eventBus,
                        final BaseLogger baseLogger,
                        @IOScheduler Scheduler scheduler,
                        @UIScheduler final Scheduler uiScheduler) {
        this.lightService = lightService;
        this.networkDetails = networkDetails;
        this.eventBus = eventBus;
        this.ioScheduler = scheduler;
        this.uiScheduler = uiScheduler;

        errorSubscriber = new ErrorSubscriber();

        logger = new Logger("LightNetwork", baseLogger);

        fetchLights(true)
                .subscribe(errorSubscriber);
    }

    /**
     * @param hue the hue to set the selected lights
     */
    public final Observable<StatusResponse> setHue(final int hue, float duration) {
        return doSetColour(makeHueString(Light.convertHue(hue)), makeDurationString(duration));
    }

    /**
     * @param saturation the saturation to set the selected lights
     */
    public final Observable<StatusResponse> setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationString(Light.convertSaturation(saturation)), makeDurationString(duration));
    }

    /**
     * @param brightness the brightness to set the selected lights
     */
    public final Observable<StatusResponse> setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessString(Light.convertBrightness(brightness)), makeDurationString(duration));
    }

    /**
     * @param kelvin the kelvin (warmth to set the selected lights
     */
    public final Observable<StatusResponse> setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinString(kelvin + Light.KELVIN_BASE), makeDurationString(duration));
    }

    /**
     * Toggles the power of the selected lights
     *
     */
    public final Observable<StatusResponse> togglePower() {
        return doToggleLights();
    }

    /**
     * @param power ON or OFF
     * @param duration how long to set the power change for
     */
    public final Observable<StatusResponse> setPower(final ModelConstants.Power power, final float duration) {
        return doSetPower(power.powerString(), makeDurationString(duration));
    }

    public final Observable<Light> fetchLights(boolean fetchFromServer) {
        logger.debug("fetchLights()");

        if(fetchFromServer || lightsObservable == null) {
            lightsObservable = lightService.listLights(networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .doOnError(new Action1<Throwable>() {
                        @Override
                        public void call(final Throwable throwable) {
                            logger.error(throwable);
                        }
                    })
                    .flatMap(new Func1<List<Light>, Observable<Light>>() {
                        @Override
                        public Observable<Light> call(List<Light> dataResponses) {
                            List<Observable<Light>> observables = new ArrayList<>(dataResponses.size());

                            for (Light dataResponse : dataResponses) {
                                logger.debug(dataResponse.toString());
                                eventBus.postMessage(new LightDetailsEvent(dataResponse))
                                        .subscribe(errorSubscriber);

                                observables.add(Observable.just(dataResponse));
                            }

                            return Observable.merge(observables);
                        }
                    })
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            eventBus.postMessage(new FetchLightsSuccessEvent())
                                    .subscribe(errorSubscriber);
                        }
                    })
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return lightsObservable;
    }

    public Observable<Light> fetchLight(final String id) {
        if(id == null) {
            return Observable.error(new IllegalArgumentException("fetchLight() id cannot be null"));
        }

        return fetchLights(false).filter(new Func1<Light, Boolean>() {
            @Override
            public Boolean call(Light light) {
                return light.id().equals(id);
            }
        });
    }

    /**
     * @param colourQuery the colour query
     * @param durationQuery the colour query
     */
    private Observable<StatusResponse> doSetColour(final String colourQuery, final String durationQuery) {
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
                        fetchLights(true)
                                .subscribe(errorSubscriber);
                    }
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    private Observable<StatusResponse> doToggleLights() {
        logger.debug("doToggleLights()");

        return lightService.togglePower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(), "")
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
                        fetchLights(true)
                                .subscribe(errorSubscriber);
                    }
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    private Observable<StatusResponse> doSetPower(final String powerQuery, final String durationQuery) {
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
                        fetchLights(true)
                                .subscribe(errorSubscriber);
                    }
                })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
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

    public static class FetchLightsSuccessEvent { }

    public static class LightDetailsEvent {
        private final Light light;

        public LightDetailsEvent(Light light) {
            this.light = light;
        }

        public final Light light() {
            return light;
        }
    }

    @Override
    public String toString() {
        return "LightNetwork{" +
                "uiScheduler=" + uiScheduler +
                ", ioScheduler=" + ioScheduler +
                ", lightService=" + lightService +
                ", eventBus=" + eventBus +
                ", networkDetails=" + networkDetails +
                '}';
    }

    private class ErrorSubscriber extends Subscriber {

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            logger.error(e.getMessage());
        }

        @Override
        public void onNext(Object o) { }
    }
}
