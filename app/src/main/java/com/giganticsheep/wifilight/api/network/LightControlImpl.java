package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.FetchedGroupsEvent;
import com.giganticsheep.wifilight.api.FetchLightNetworkEvent;
import com.giganticsheep.wifilight.api.FetchedLightsEvent;
import com.giganticsheep.wifilight.api.FetchedLocationsEvent;
import com.giganticsheep.wifilight.api.GroupFetchedEvent;
import com.giganticsheep.wifilight.api.LightFetchedEvent;
import com.giganticsheep.wifilight.api.LocationFetchedEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.api.model.WifiLightData;
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
    private Observable<List<LightResponse>> lightResponsesObservable = null;

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
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)))
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
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Location> fetchLocations(final boolean fetchFromServer) {
        final int[] locationCount = new int[1];

        return fetchLightResponses(fetchFromServer)
                .doOnError(throwable -> Timber.e(throwable, "fetchLocations()"))
                .flatMap(lightResponses -> {
                    List<Observable<Location>> observables = new ArrayList<>();
                    Map<String, Location> locations = new HashMap<>();

                    for (Light lightResponse : lightResponses) {
                        Timber.d(lightResponse.toString());

                        Location location = lightResponse.getLocation();
                        if (!locations.containsKey(location.getId())) {
                            locations.put(location.getId(), location);
                            observables.add(Observable.just(location));
                            locationCount[0]++;

                            eventBus.postMessage(new LocationFetchedEvent(location))
                                    .subscribe(new ErrorSubscriber<>());
                        }
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> eventBus.postMessage(new FetchedLocationsEvent(locationCount[0]))
                        .subscribe(new ErrorSubscriber<>()));
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Group> fetchGroups(final boolean fetchFromServer) {
        final int[] groupCount = new int[1];

        return fetchLightResponses(false)
                .doOnError(throwable -> Timber.e(throwable, "fetchGroups()"))
                .flatMap(lightResponses -> {
                    List<Observable<Group>> observables = new ArrayList<>();
                    Map<String, Group> groups = new HashMap<>();

                    for (Light lightResponse : lightResponses) {
                        Group group = lightResponse.getGroup();
                        if (!groups.containsKey(group.getId())) {
                            groups.put(group.getId(), group);
                            observables.add(Observable.just(group));
                            groupCount[0]++;

                            if(fetchFromServer) {
                                eventBus.postMessage(new GroupFetchedEvent(group))
                                        .subscribe(new ErrorSubscriber<>());
                            }
                        }
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> {
                    if(fetchFromServer) {
                        eventBus.postMessage(new FetchedGroupsEvent(groupCount[0]))
                                .subscribe(new ErrorSubscriber<>());
                    }
                });
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Light> fetchLights(final boolean fetchFromServer) {
        final int[] lightsCount = new int[1];

        return fetchLightResponses(false)
                .doOnError(throwable -> Timber.e(throwable, "fetchLights()"))
                .flatMap(lightResponses -> {
                    List<Observable<Light>> observables = new ArrayList<>();

                    for (Light lightResponse : lightResponses) {
                        if(fetchFromServer) {
                            eventBus.postMessage(new LightFetchedEvent(lightResponse))
                                    .subscribe(new ErrorSubscriber<>());
                        }

                        observables.add(Observable.just(lightResponse));
                        lightsCount[0]++;
                    }

                    return Observable.merge(observables);
                })
                .doOnCompleted(() -> {
                    if (fetchFromServer) {
                        eventBus.postMessage(new FetchedLightsEvent(lightsCount[0]))
                                .subscribe(new ErrorSubscriber<>());
                    }
                });

    }

    @DebugLog
    @NonNull
    @Override
    public Observable<Location> fetchLightNetwork() {
        List<Observable<? extends WifiLightData>> observables = new ArrayList<>();

        observables.add(fetchLocations(true));
        observables.add(fetchGroups(true));
        observables.add(fetchLights(true));

        return Observable.zip(observables, args -> (Location) args[0])
                .doOnCompleted(() -> eventBus.postMessage(new FetchLightNetworkEvent())
                        .subscribe(new ErrorSubscriber<>()));
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Light> fetchLight(@Nullable final String id) {
        if(id == null) {
            return Observable.error(new IllegalArgumentException("fetchLight() id cannot be null"));
        }

        return fetchLights(false).filter(light -> light.getId().equals(id))
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
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @NonNull
    private Observable<List<LightResponse>> fetchLightResponses(final boolean fetchFromServer) {
        if(fetchFromServer || lightResponsesObservable == null) {
            lightResponsesObservable = lightService.listLights(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return lightResponsesObservable;
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
