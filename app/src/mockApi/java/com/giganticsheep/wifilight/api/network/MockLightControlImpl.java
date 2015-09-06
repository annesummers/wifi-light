package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.api.network.test.MockLight;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.util.Constants;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 15/07/15. <p>
 * (*_*)
 */
public class MockLightControlImpl implements LightControl {

    private static final String INITIAL_POWER = "off";

    private final LightNetwork testLightNetwork;
    private final EventBus eventBus;

    private Status status;
    private long timeout = 0L;

    private final ErrorSubscriber errorSubscriber;

    public MockLightControlImpl(@NonNull final EventBus eventBus,
                                @NonNull final ErrorStrings errorStrings,
                                @IOScheduler final Scheduler ioScheduler,
                                @UIScheduler final Scheduler uiScheduler,
                                @NonNull final LightNetwork testLightNetwork) {
        this.eventBus = eventBus;
        this.testLightNetwork = testLightNetwork;

        this.status = Status.OK;
        this.errorSubscriber = new ErrorSubscriber(eventBus, errorStrings);
    }

    @NonNull
    @Override
    public Observable<LightStatus> setHue(final int hue, final float duration) {
        switch (status) {
            case OK:
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                            for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                light.color.hue = hue;

                                StatusResponse statusResponse = new StatusResponse();
                                statusResponse.id = light.getId();
                                statusResponse.label = light.getLabel();
                                statusResponse.status = Status.OK.getStatusString();

                                subscriber.onNext(statusResponse);
                            }
                        }
                    }

                    subscriber.onCompleted();
                }});
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setSaturation(final int saturation, float duration) {
        switch (status) {
            case OK:
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                            for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                light.color.saturation = LightConstants.convertSaturation(saturation);

                                StatusResponse statusResponse = new StatusResponse();
                                statusResponse.id = light.getId();
                                statusResponse.label = light.getLabel();
                                statusResponse.status = Status.OK.getStatusString();

                                subscriber.onNext(statusResponse);
                            }
                        }
                    }

                    subscriber.onCompleted();
                }});
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setBrightness(final int brightness, float duration) {
        switch (status) {
            case OK:
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                            for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                light.brightness = LightConstants.convertBrightness(brightness);

                                StatusResponse statusResponse = new StatusResponse();
                                statusResponse.id = light.getId();
                                statusResponse.label = light.getLabel();
                                statusResponse.status = Status.OK.getStatusString();

                                subscriber.onNext(statusResponse);
                            }
                        }
                    }

                    subscriber.onCompleted();
                }});
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setKelvin(final int kelvin, float duration) {
        switch (status) {
            case OK:
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                            for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                light.color.kelvin = kelvin;

                                StatusResponse statusResponse = new StatusResponse();
                                statusResponse.id = light.getId();
                                statusResponse.label = light.getLabel();
                                statusResponse.status = Status.OK.getStatusString();

                                subscriber.onNext(statusResponse);
                            }
                        }
                    }

                    subscriber.onCompleted();
                }});
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> togglePower() {
        switch (status) {
            case OK:
            return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                @Override
                public void call(Subscriber<? super LightStatus> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                            for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                light.power = (light.getPower() == Power.ON) ? Power.OFF.getPowerString() : Power.ON.getPowerString();

                                StatusResponse statusResponse = new StatusResponse();
                                statusResponse.id = light.getId();
                                statusResponse.label = light.getLabel();
                                statusResponse.status = Status.OK.getStatusString();

                                subscriber.onNext(statusResponse);
                            }
                        }
                    }

                    subscriber.onCompleted();
                }});
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightStatus> setPower(final Power power, float duration) {
        switch (status) {
            case OK:
                return Observable.create(new Observable.OnSubscribe<LightStatus>() {

                    @Override
                    public void call(Subscriber<? super LightStatus> subscriber) {
                        for (int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                            for (int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                                for (int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                    MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                    light.power = power.getPowerString();

                                    StatusResponse statusResponse = new StatusResponse();
                                    statusResponse.id = light.getId();
                                    statusResponse.label = light.getLabel();
                                    statusResponse.status = Status.OK.getStatusString();

                                    subscriber.onNext(statusResponse);
                                }
                            }
                        }

                        subscriber.onCompleted();
                    }
                });
            case OFF:
                return offObservable();
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    private Observable<Light> fetchLights(boolean fetchFromServer) {
        switch (status) {
            case OK:
                return Observable.create(new Observable.OnSubscribe<Light>() {

                    @Override
                    public void call(Subscriber<? super Light> subscriber) {
                        for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                            for (int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                                for (int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                    MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                    light.connected = true;
                                    light.seconds_since_seen = timeout;

                                    subscriber.onNext(light);
                                }
                            }
                        }

                        subscriber.onCompleted();
                    }
                });
            case OFF:
                return Observable.create(new Observable.OnSubscribe<Light>() {

                    @Override
                    public void call(Subscriber<? super Light> subscriber) {
                        for (int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                            for (int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                                for (int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                                    MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                                    light.connected = false;
                                    light.seconds_since_seen = timeout;

                                    subscriber.onNext(light);
                                }
                            }
                        }

                        subscriber.onCompleted();
                    }
                });
            case ERROR:
                return Observable.error(new Throwable("Error from server"));
            default:
                return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<Light> fetchLight(final String id) {
        return fetchLights(false)
                .filter(light -> light.getId().equals(id));
    }

    @NonNull
    @Override
    public Observable<Group> fetchGroup(String groupId) {
        return null;
    }

    @NonNull
    @Override
    public Observable<Location> fetchLocation(final String locationId) {
        if (status == Status.OK || status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Location>() {

                @Override
                public void call(Subscriber<? super Location> subscriber) {
                    for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                        Location location = testLightNetwork.getLightLocation(i);
                        if(location.getId().equals(locationId)) {
                            subscriber.onNext(location);
                            subscriber.onCompleted();
                        }
                    }
                }
            })
            .doOnCompleted(() -> eventBus.postMessage(new FetchLightNetworkEvent(testLightNetwork))
                    .subscribe(errorSubscriber));
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    @NonNull
    @Override
    public Observable<LightNetwork> fetchLightNetwork() {
        if (status == Status.OK || status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<LightNetwork>() {

                @Override
                public void call(Subscriber<? super LightNetwork> subscriber) {
                    subscriber.onNext(testLightNetwork);
                    subscriber.onCompleted();
                }
            })
            .doOnCompleted(() -> eventBus.postMessage(new FetchLightNetworkEvent(testLightNetwork))
                    .subscribe(errorSubscriber));
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }

    private Observable<LightStatus> offObservable() {
        return Observable.create(new Observable.OnSubscribe<LightStatus>() {

            @Override
            public void call(Subscriber<? super LightStatus> subscriber) {
                for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
                    for(int j = 0; j < testLightNetwork.lightGroupCount(i); j++) {
                        for(int k = 0; k < testLightNetwork.lightCount(i, j); k++) {
                            MockLight light = (MockLight) testLightNetwork.getLight(i, j, k);

                            StatusResponse statusResponse = new StatusResponse();
                            statusResponse.id = light.getId();
                            statusResponse.label = light.getLabel();
                            statusResponse.status = Status.OFF.getStatusString();

                            subscriber.onNext(statusResponse);
                        }
                    }
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
