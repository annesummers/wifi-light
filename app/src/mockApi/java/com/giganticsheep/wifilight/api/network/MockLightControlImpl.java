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
import com.giganticsheep.wifilight.ui.control.LightNetwork;
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

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private final LightNetwork testLightNetwork;

    // private final List<LightResponse> lights = new ArrayList<>();
   // private final List<GroupData> groups = new ArrayList<>();
   // private final List<GroupData> locations = new ArrayList<>();

    private Status status;
    private long timeout = 0L;

    public MockLightControlImpl(@NonNull final EventBus eventBus,
                                @IOScheduler final Scheduler ioScheduler,
                                @UIScheduler final Scheduler uiScheduler,
                                @NonNull final LightNetwork testLightNetwork) {
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

      /*  LightResponse light = new LightResponse(Constants.TEST_ID);
        light.label = Constants.TEST_LABEL;
        light.power = INITIAL_POWER;
        lights.addLightLocation(light);

        light = new LightResponse(Constants.TEST_ID2);
        light.label = Constants.TEST_LABEL2;
        light.power = INITIAL_POWER;
        lights.addLightLocation(light);

        GroupData group = new GroupData();
        group.id = Constants.TEST_GROUP_ID;
        group.name = Constants.TEST_GROUP_LABEL;
        groups.addLightLocation(group);

        group = new GroupData();
        group.id = Constants.TEST_GROUP_ID2;
        group.name = Constants.TEST_GROUP_LABEL2;
        groups.addLightLocation(group);

        GroupData location = new GroupData();
        location.id = Constants.TEST_LOCATION_ID;
        location.name = Constants.TEST_LOCATION_LABEL;
        groups.addLightLocation(location);

        location.id = Constants.TEST_LOCATION_ID2;
        location.name = Constants.TEST_LOCATION_LABEL2;
        groups.addLightLocation(location);*/

        this.testLightNetwork = testLightNetwork;
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
                        for(int i = 0; i < testLightNetwork.lightLocationCount(); i++) {
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
    public Observable<Location> fetchLocation(String locationId) {
        return null;
    }

   /* @NonNull
    @Override
    public Observable<Group> fetchGroups(boolean fetchFromServer) {
        if (status == Status.OK) {
            return Observable.create(new Observable.OnSubscribe<Group>() {

                @Override
                public void call(Subscriber<? super Group> subscriber) {
                    for (GroupData group : groups) {

                        eventBus.postMessage(new GroupFetchedEvent(group));
                        subscriber.onNext(group);
                    }

                    eventBus.postMessage(new FetchedGroupsEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.OFF) {
            return Observable.create(new Observable.OnSubscribe<Group>() {

                @Override
                public void call(Subscriber<? super Group> subscriber) {
                    for (GroupData group : groups) {

                        eventBus.postMessage(new GroupFetchedEvent(group));
                        subscriber.onNext(group);
                    }

                    eventBus.postMessage(new FetchedGroupsEvent(lights.size()));
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

                        eventBus.postMessage(new LocationFetchedEvent(location));
                        subscriber.onNext(location);
                    }

                    eventBus.postMessage(new FetchedLocationsEvent(lights.size()));
                    subscriber.onCompleted();
                }
            });
        } else if (status == Status.ERROR) {
            return Observable.error(new Throwable("Error from server"));
        } else {
            return Observable.error(new Throwable("Status unknown"));
        }
    }*/

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
