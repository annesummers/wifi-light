package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;

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
    private final Subscriber errorSubscriber = new ErrorSubscriber();

    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Light> lightsObservable = null;

    // TODO groups
    // TODO locations
    // TODO selectors
    // TODO effects
    // TODO connected can we find the light?

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    private final NetworkDetails networkDetails;

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

        logger = new Logger(getClass().getName(), baseLogger);

        /*fetchLights(true)
                .subscribe(errorSubscriber);*/
    }

    @Override
    public final Observable<StatusResponse> setHue(final int hue, float duration) {
        return doSetColour(makeHueQuery(LightConstants.convertHue(hue)), makeDurationQuery(duration));
    }

    @Override
    public final Observable<StatusResponse> setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationQuery(LightConstants.convertSaturation(saturation)), makeDurationQuery(duration));
    }

    @Override
    public final Observable<StatusResponse> setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessQuery(LightConstants.convertBrightness(brightness)), makeDurationQuery(duration));
    }

    @Override
    public final Observable<StatusResponse> setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinQuery(kelvin + LightConstants.KELVIN_BASE), makeDurationQuery(duration));
    }

    @Override
    public final Observable<StatusResponse> togglePower() {
        logger.debug("togglePower()");

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

    @Override
    public final Observable<StatusResponse> setPower(final Power power, final float duration) {
        String powerQuery = power.powerString();
        String durationQuery =  makeDurationQuery(duration);

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
        })
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler);
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

    private String authorisation() {
        return NetworkConstants.LABEL_BEARER + NetworkConstants.SPACE + networkDetails.getApiKey();
    }

    private String makeHueQuery(final double hue) {
        return NetworkConstants.LABEL_HUE + Double.toString(hue);
    }

    private String makeSaturationQuery(final double saturation) {
        return NetworkConstants.LABEL_SATURATION + Double.toString(saturation);
    }

    private String makeKelvinQuery(final long kelvin) {
        return NetworkConstants.LABEL_KELVIN + Long.toString(kelvin);
    }

    private String makeBrightnessQuery(final double brightness) {
        return NetworkConstants.LABEL_BRIGHTNESS + Double.toString(brightness);
    }

    private String makeDurationQuery(final float duration) {
        return Float.toString(duration);
    }

    @Override
    public String toString() {
        return "LightNetwork{" +
                "uiScheduler=" + uiScheduler +
                ", ioScheduler=" + ioScheduler +
                ", lightService=" + lightService +
                ", networkDetails=" + networkDetails +
                '}';
    }

    public interface Injector {
        void inject(LightControlImpl lightNetwork);
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
