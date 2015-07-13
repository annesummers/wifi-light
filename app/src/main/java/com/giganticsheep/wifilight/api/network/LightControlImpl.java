package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.dagger.ApplicationScope;
import com.giganticsheep.wifilight.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.dagger.UIScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 *
 * An implementation of LightControl to handle the communication with the server.
 */

@ApplicationScope
class LightControlImpl implements LightControl {

    @SuppressWarnings("FieldNotUsedInToString")
    private final Logger logger;

    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Light> lightsObservable;

    @SuppressWarnings("FieldNotUsedInToString")
    private final Subscriber errorSubscriber;

    // TODO groups
    // TODO locations
    // TODO selectors
    // TODO effects
    // TODO connected can we find the light?

    private final NetworkDetails networkDetails;
    private final EventBus eventBus;

    private final LightService lightService;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    public LightControlImpl(final NetworkDetails networkDetails,
                            final LightService lightService,
                            final EventBus eventBus,
                            final BaseLogger baseLogger,
                            @IOScheduler final Scheduler ioScheduler,
                            @UIScheduler final Scheduler uiScheduler) {
        this.lightService = lightService;
        this.networkDetails = networkDetails;
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        errorSubscriber = new ErrorSubscriber();

        logger = new Logger("LightNetwork", baseLogger);

        fetchLights(true)
                .subscribe(errorSubscriber);
    }

    @Override
    public final Observable<StatusResponse> setHue(final int hue, float duration) {
        return doSetColour(makeHueString(LightConstants.convertHue(hue)), makeDurationString(duration));
    }

    @Override
    public final Observable<StatusResponse> setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationString(LightConstants.convertSaturation(saturation)), makeDurationString(duration));
    }

    @Override
    public final Observable<StatusResponse> setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessString(LightConstants.convertBrightness(brightness)), makeDurationString(duration));
    }

    @Override
    public final Observable<StatusResponse> setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinString(kelvin + LightConstants.KELVIN_BASE), makeDurationString(duration));
    }

    @Override
    public final Observable<StatusResponse> togglePower() {
        return doToggleLights();
    }

    @Override
    public final Observable<StatusResponse> setPower(final Power power, final float duration) {
        return doSetPower(power.powerString(), makeDurationString(duration));
    }

    @Override
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
                    .flatMap(new Func1<List<LightResponse>, Observable<Light>>() {
                        @Override
                        public Observable<Light> call(List<LightResponse> lightResponses) {
                            List<Observable<Light>> observables = new ArrayList(lightResponses.size());

                            for (Light lightResponse : lightResponses) {
                                logger.debug(lightResponse.toString());

                                eventBus.postMessage(new LightDetailsEvent(lightResponse))
                                        .subscribe(errorSubscriber);

                                observables.add(Observable.just(lightResponse));
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

    @Override
    public final Observable<Light> fetchLight(final String id) {
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

    public interface Injector {
        void inject(LightControlImpl lightNetwork);
    }
}
