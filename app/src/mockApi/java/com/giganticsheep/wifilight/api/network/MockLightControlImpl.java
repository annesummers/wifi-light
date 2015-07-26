package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.FetchGroupsEvent;
import com.giganticsheep.wifilight.api.FetchLightsEvent;
import com.giganticsheep.wifilight.api.FetchLocationsEvent;
import com.giganticsheep.wifilight.api.FetchedGroupEvent;
import com.giganticsheep.wifilight.api.FetchedLightEvent;
import com.giganticsheep.wifilight.api.FetchedLocationEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.util.Constants;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import java.util.ArrayList;
import java.util.List;

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

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final List<LightResponse> lights = new ArrayList<>();
    private final List<GroupData> groups = new ArrayList<>();
    private final List<GroupData> locations = new ArrayList<>();

    private Status status;
    private long timeout = 0L;

    public MockLightControlImpl(final EventBus eventBus,
                                @IOScheduler final Scheduler ioScheduler,
                                @UIScheduler final Scheduler uiScheduler) {
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        LightResponse light = new LightResponse(Constants.TEST_ID);
        light.label = Constants.TEST_LABEL;
        light.power = INITIAL_POWER;
        lights.add(light);

        light = new LightResponse(Constants.TEST_ID2);
        light.label = Constants.TEST_LABEL2;
        light.power = INITIAL_POWER;
        lights.add(light);

        GroupData group = new GroupData();
        group.id = Constants.TEST_GROUP_ID;
        group.name = Constants.TEST_GROUP_LABEL;
        groups.add(group);

        group = new GroupData();
        group.id = Constants.TEST_GROUP_ID2;
        group.name = Constants.TEST_GROUP_LABEL2;
        groups.add(group);

        GroupData location = new GroupData();
        location.id = Constants.TEST_LOCATION_ID;
        location.name = Constants.TEST_LOCATION_LABEL;
        groups.add(location);

        location.id = Constants.TEST_LOCATION_ID2;
        location.name = Constants.TEST_LOCATION_LABEL2;
        groups.add(location);
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
                        light.connected = true;
                        light.seconds_since_seen = timeout;

                        eventBus.postMessage(new FetchedLightEvent(light));
                        subscriber.onNext(light);
                    }

                    eventBus.postMessage(new FetchLightsEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Light>() {

                @Override
                public void call(Subscriber<? super Light> subscriber) {
                    for (LightResponse light : lights) {
                        light.connected = false;
                        light.seconds_since_seen = timeout;

                        eventBus.postMessage(new FetchedLightEvent(light));
                        subscriber.onNext(light);
                    }

                    eventBus.postMessage(new FetchLightsEvent(lights.size()));
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
                .filter(light -> light.id().equals(id));
    }

    @NonNull
    @Override
    public Observable<Group> fetchGroups(boolean fetchFromServer) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<Group>() {

                @Override
                public void call(Subscriber<? super Group> subscriber) {
                    for (GroupData group : groups) {

                        eventBus.postMessage(new FetchedGroupEvent(group));
                        subscriber.onNext(group);
                    }

                    eventBus.postMessage(new FetchGroupsEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Group>() {

                @Override
                public void call(Subscriber<? super Group> subscriber) {
                    for (GroupData group : groups) {

                        eventBus.postMessage(new FetchedGroupEvent(group));
                        subscriber.onNext(group);
                    }

                    eventBus.postMessage(new FetchGroupsEvent(lights.size()));
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
    public Observable<Location> fetchLocations(boolean fetchFromServer) {
        if (status == Status.OK || status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Location>() {

                @Override
                public void call(Subscriber<? super Location> subscriber) {
                    for (Location location : locations) {

                        eventBus.postMessage(new FetchedLocationEvent(location));
                        subscriber.onNext(location);
                    }

                    eventBus.postMessage(new FetchLocationsEvent(lights.size()));
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
    public Observable<Location> fetchLightNetwork() {
        if (status == Status.OK || status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Location>() {

                @Override
                public void call(Subscriber<? super Location> subscriber) {
                    fetchLocations(true).subscribe(new Subscriber<Location>() {
                        @Override
                        public void onCompleted() {
                            subscriber.onCompleted();

                            fetchGroups(true).subscribe(new ErrorSubscriber<>());
                            fetchLights(true).subscribe(new ErrorSubscriber<>());
                        }

                        @Override
                        public void onError(Throwable e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onNext(Location location) {
                            subscriber.onNext(location);
                        }
                    });
                }
            });
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
