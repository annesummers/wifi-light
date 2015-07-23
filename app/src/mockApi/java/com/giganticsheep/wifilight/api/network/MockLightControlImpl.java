package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.util.Constants;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class MockLightControlImpl implements LightControl {

    private static final String INITIAL_POWER = "off";

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private List<LightResponse> lights = new ArrayList();

    private Status status;
    private long timeout = 0L;

    public MockLightControlImpl(final EventBus eventBus,
                                final BaseLogger baseLogger,
                                @IOScheduler final Scheduler ioScheduler,
                                @UIScheduler final Scheduler uiScheduler) {
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        LightResponse light = new LightResponse(Constants.TEST_ID);
        light.label = Constants.TEST_NAME;
        lights.add(light);

        light = new LightResponse(Constants.TEST_ID2);
        light.label = Constants.TEST_NAME2;
        lights.add(light);
    }

    @NonNull
    @Override
    public Observable<LightStatus> setHue(final int hue, final float duration) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.color.hue = hue;

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF) {
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setSaturation(final int saturation, float duration) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.color.saturation = LightConstants.convertSaturation(saturation);

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF) {
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setBrightness(final int brightness, float duration) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.brightness = LightConstants.convertBrightness(brightness);

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF) {
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setKelvin(final int kelvin, float duration) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.color.kelvin = kelvin;

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF){
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> togglePower() {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.power = (light.getPower() == Power.ON) ? Power.OFF.getPowerString() : Power.ON.getPowerString();

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF) {
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setPower(final Power power, float duration) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(LightResponse light : lights) {
                        light.power = power.getPowerString();

                        StatusResponse statusResponse = new StatusResponse();
                        statusResponse.id = light.id();
                        statusResponse.label = light.getLabel();
                        statusResponse.status = Status.OK.getStatusString();

                        subscriber.onNext(statusResponse);
                    }

                    subscriber.onCompleted();
                }});
        } else if (status == Status.OFF) {
            return offObservable();
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<Light> fetchLights(boolean fetchFromServer) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<Light>() {

                @Override
                public void call(Subscriber<? super Light> subscriber) {
                    for (LightResponse light : lights) {
                        light.power = INITIAL_POWER;
                        light.connected = true;
                        light.seconds_since_seen = timeout;

                        eventBus.postMessage(new FetchedLightEvent(light));
                        subscriber.onNext(light);
                    }

                    eventBus.postMessage(new FetchLightsSuccessEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Light>() {

                @Override
                public void call(Subscriber<? super Light> subscriber) {
                    for (LightResponse light : lights) {
                        light.power = INITIAL_POWER;
                        light.connected = false;
                        light.seconds_since_seen = timeout;

                        eventBus.postMessage(new FetchedLightEvent(light));
                        subscriber.onNext(light);
                    }

                    eventBus.postMessage(new FetchLightsSuccessEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<Light> fetchLight(final String id) {
        return fetchLights(false)
                .filter(new Func1<Light, Boolean>() {
                    @Override
                    public Boolean call(Light light) {
                        return light.id().equals(id);
                    }
                });
    }

    private Observable<LightStatus> offObservable() {
        return Observable.create(new Observable.OnSubscribe<LightStatus>() {

            @Override
            public void call(Subscriber<? super LightStatus> subscriber) {
                for (LightResponse light : lights) {
                    StatusResponse statusResponse = new StatusResponse();
                    statusResponse.id = light.id();
                    statusResponse.label = light.getLabel();
                    statusResponse.status = Status.OFF.getStatusString();

                    subscriber.onNext(statusResponse);
                }

                subscriber.onCompleted();
            }
        });
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setLightTimeout(boolean timeout) {
        this.timeout = timeout ? Constants.LAST_SEEN_TIMEOUT_SECONDS + 1L : 0L;
    }
}
