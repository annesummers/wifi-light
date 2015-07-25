package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.ApplicationScope;
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
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Scheduler;
import timber.log.Timber;

/**
 * An implementation of LightControl to handle the communication with the server.
 * <p>
 * Created by anne on 22/06/15.
 * <p>
 *     (*_*)
 */

@ApplicationScope
class LightControlImpl implements LightControl {

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Light> lightsObservable = null;
    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Location> locationsObservable = null;
    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Group> groupsObservable = null;

    // TODO groups
    // TODO locations
    // TODO selectors
    // TODO effects

    private final NetworkDetails networkDetails;
    private final LightService lightService;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    public LightControlImpl(final NetworkDetails networkDetails,
                            final LightService lightService,
                            final EventBus eventBus,
                            @IOScheduler final Scheduler ioScheduler,
                            @UIScheduler final Scheduler uiScheduler) {
        this.lightService = lightService;
        this.networkDetails = networkDetails;
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setHue(final int hue, float duration) {
        return doSetColour(makeHueQuery(LightConstants.convertHue(hue)), makeDurationQuery(duration));
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationQuery(LightConstants.convertSaturation(saturation)), makeDurationQuery(duration));
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessQuery(LightConstants.convertBrightness(brightness)), makeDurationQuery(duration));
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinQuery(kelvin + LightConstants.KELVIN_BASE), makeDurationQuery(duration));
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<LightStatus> togglePower() {
        return lightService.togglePower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(), "")
                .doOnError(throwable -> Timber.e(throwable, "togglePower()"))
                .flatMap(LightStatuses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(LightStatuses.size());

                    for (LightStatus LightStatus : LightStatuses) {
                        Timber.d(LightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> fetchLights(true)
                        .subscribe(new ErrorSubscriber<>()))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<LightStatus> setPower(@NonNull final Power power,
                                                  final float duration) {
        String powerQuery = power.getPowerString();
        String durationQuery =  makeDurationQuery(duration);

        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(NetworkConstants.URL_STATE, powerQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);

        return lightService.setPower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(),
                queryMap)
                .doOnError(throwable -> Timber.e(throwable, "setPower()"))
                .flatMap(LightStatuses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(LightStatuses.size());

                    for (LightStatus LightStatus : LightStatuses) {
                        Timber.d(LightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> fetchLights(true)
                        .subscribe(new ErrorSubscriber<>()))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Location> fetchLocations(final boolean fetchFromServer) {
        if(fetchFromServer || locationsObservable == null) {
            final int[] locationCount = new int[1];

            locationsObservable = lightService.listLights(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .doOnError(throwable -> Timber.e(throwable, "fetchLocations()"))
                    .flatMap(lightResponses -> {
                        List<Observable<Location>> observables = new ArrayList<>();
                        Map<String, Location> locations = new HashMap<>();

                        for (Light lightResponse : lightResponses) {
                            Timber.d(lightResponse.toString());

                            Location location = lightResponse.getLocation();
                            if (!locations.containsKey(location.id())) {
                                locations.put(location.id(), location);
                                observables.add(Observable.just(location));
                                locationCount[0]++;

                                eventBus.postMessage(new FetchedLocationEvent(location))
                                        .subscribe(new ErrorSubscriber<>());
                            }
                        }

                        return Observable.merge(observables);
                    })
                    .doOnCompleted(() -> eventBus.postMessage(new FetchLocationsEvent(locationCount[0]))
                            .subscribe(new ErrorSubscriber<>()))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return locationsObservable;
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Group> fetchGroups(final boolean fetchFromServer) {
        if(fetchFromServer || groupsObservable == null) {
            final int[] groupCount = new int[1];

            groupsObservable = lightService.listLights(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .doOnError(throwable -> Timber.e(throwable, "fetchLocations()"))
                    .flatMap(lightResponses -> {
                        List<Observable<Group>> observables = new ArrayList<>();
                        Map<String, Group> groups = new HashMap<>();

                        for (Light lightResponse : lightResponses) {
                            Timber.d(lightResponse.toString());

                            Group group = lightResponse.getGroup();
                            if (!groups.containsKey(group.id())) {
                                groups.put(group.id(), group);
                                observables.add(Observable.just(group));
                                groupCount[0]++;

                                eventBus.postMessage(new FetchedGroupEvent(group))
                                        .subscribe(new ErrorSubscriber<>());
                            }
                        }

                        return Observable.merge(observables);
                    })
                    .doOnCompleted(() -> eventBus.postMessage(new FetchGroupsEvent(groupCount[0]))
                            .subscribe(new ErrorSubscriber<>()))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return groupsObservable;
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Light> fetchLights(final boolean fetchFromServer) {
        if(fetchFromServer || lightsObservable == null) {
            final int[] lightsCount = new int[1];

            lightsObservable = lightService.listLights(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .doOnError(throwable -> Timber.e(throwable, "fetchLights()"))
                    .flatMap(lightResponses -> {
                        List<Observable<Light>> observables = new ArrayList<>();

                        for (Light lightResponse : lightResponses) {
                            Timber.d(lightResponse.toString());

                            eventBus.postMessage(new FetchedLightEvent(lightResponse))
                                    .subscribe(new ErrorSubscriber<>());

                            observables.add(Observable.just(lightResponse));
                            lightsCount[0]++;
                        }

                        return Observable.merge(observables);
                    })
                    .doOnCompleted(() -> eventBus.postMessage(new FetchLightsEvent(lightsCount[0]))
                            .subscribe(new ErrorSubscriber<>()))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return lightsObservable;
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Light> fetchLight(@Nullable final String id) {
        if(id == null) {
            return Observable.error(new IllegalArgumentException("fetchLight() id cannot be null"));
        }

        return fetchLights(false).filter(light -> light.id().equals(id))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @DebugLog
    @NonNull
    private Observable<LightStatus> doSetColour(@Nullable final String colourQuery,
                                                @Nullable final String durationQuery) {
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
                .doOnError(throwable -> Timber.e(throwable, "setColour"))
                .flatMap(LightStatuses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(LightStatuses.size());

                    for (LightStatus LightStatus : LightStatuses) {
                        Timber.d(LightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> fetchLights(true)
                        .subscribe(new ErrorSubscriber<Light>()))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @NonNull
    private String authorisation() {
        return NetworkConstants.LABEL_BEARER + NetworkConstants.SPACE + networkDetails.getApiKey();
    }

    @NonNull
    private String makeHueQuery(final double hue) {
        return NetworkConstants.LABEL_HUE + Double.toString(hue);
    }

    @NonNull
    private String makeSaturationQuery(final double saturation) {
        return NetworkConstants.LABEL_SATURATION + Double.toString(saturation);
    }

    @NonNull
    private String makeKelvinQuery(final long kelvin) {
        return NetworkConstants.LABEL_KELVIN + Long.toString(kelvin);
    }

    @NonNull
    private String makeBrightnessQuery(final double brightness) {
        return NetworkConstants.LABEL_BRIGHTNESS + Double.toString(brightness);
    }

    private String makeDurationQuery(final float duration) {
        return Float.toString(duration);
    }

    @Override
    public String toString() {
        return "LightControlImpl{" +
                "networkDetails=" + networkDetails +
                ", lightService=" + lightService +
                ", ioScheduler=" + ioScheduler +
                ", uiScheduler=" + uiScheduler +
                '}';
    }
}
