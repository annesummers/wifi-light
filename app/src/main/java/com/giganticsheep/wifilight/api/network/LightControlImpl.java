package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.api.model.WifiLightData;
import com.giganticsheep.wifilight.api.network.error.WifiLightAPIException;
import com.giganticsheep.wifilight.api.network.error.WifiLightServerException;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.base.ErrorEvent;
import com.giganticsheep.wifilight.ui.control.LightNetwork;
import com.giganticsheep.wifilight.util.ErrorSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hugo.weaving.DebugLog;
import retrofit.RetrofitError;
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

    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<List<LightResponse>> lightResponsesObservable = null;

    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Light> fetchLightsObservable = null;

    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Group> fetchGroupsObservable = null;

    @Nullable
    @SuppressWarnings("FieldNotUsedInToString")
    private Observable<Location> fetchLocationsObservable = null;

    @SuppressWarnings("FieldNotUsedInToString")
    private final EventBus eventBus;

    private final NetworkDetails networkDetails;
    private final LightService lightService;

    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    private LightSelector currentSelector = new LightSelector(LightSelector.SelectorType.ALL, null);

    public LightControlImpl(@NonNull final NetworkDetails networkDetails,
                            @NonNull final LightService lightService,
                            @NonNull final EventBus eventBus,
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
        return doSetColour(makeHueQuery(LightConstants.convertHue(hue)), makeDurationQuery(duration), currentSelector);
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setSaturation(final int saturation, float duration) {
        return doSetColour(makeSaturationQuery(LightConstants.convertSaturation(saturation)), makeDurationQuery(duration), currentSelector);
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setBrightness(final int brightness, float duration) {
        return doSetColour(makeBrightnessQuery(LightConstants.convertBrightness(brightness)), makeDurationQuery(duration), currentSelector);
    }

    @NonNull
    @Override
    public final Observable<LightStatus> setKelvin(final int kelvin, float duration) {
        return doSetColour(makeKelvinQuery(kelvin + LightConstants.KELVIN_BASE), makeDurationQuery(duration), currentSelector);
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<LightStatus> togglePower() {
        return lightService.togglePower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(), "")
                .flatMap(LightStatuses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(LightStatuses.size());

                    for (LightStatus LightStatus : LightStatuses) {
                        Timber.d(LightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .onErrorResumeNext(throwable -> {
                    return handleNetworkError(throwable);
                })
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)));
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<LightStatus> setPower(@NonNull final Power power,
                                                  final float duration) {
        String powerQuery = power.getPowerString();
        String durationQuery =  makeDurationQuery(duration);

        Map<String, String> queryMap = new ArrayMap<>();
        queryMap.put(NetworkConstants.URL_STATE, powerQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);

        return lightService.setPower(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                NetworkConstants.URL_ALL,
                authorisation(),
                queryMap)
                .flatMap(LightStatuses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(LightStatuses.size());

                    for (LightStatus LightStatus : LightStatuses) {
                        Timber.d(LightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .onErrorResumeNext(throwable -> {
                    return handleNetworkError(throwable);
                })
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)));
    }

    @DebugLog
    @NonNull
    private synchronized Observable<Location> fetchLocations(final boolean fetchFromServer) {
        if(fetchLocationsObservable == null) {
            fetchLocationsObservable = fetchLightResponses(fetchFromServer)
                    .flatMap(lightResponses -> {
                        List<Observable<Location>> observables = new ArrayList<>();
                        Map<String, Location> locations = new ArrayMap<>();

                        for (LightResponse light : lightResponses) {
                            Timber.d(light.toString());

                            Location location = light.getLocation();
                            if (!locations.containsKey(location.getId())) {
                                locations.put(location.getId(), location);

                                if (!location.containsGroup(light.getGroup().getId())) {
                                    location.addGroup(light.getGroup());
                                }

                                location.getGroup(light.getGroup().getId()).addLight(light);

                                observables.add(Observable.just(location));
                            }
                        }

                        return Observable.merge(observables);
                    });
        }

        return fetchLocationsObservable;
    }

    @DebugLog
    @NonNull
    private synchronized Observable<Group> fetchGroups() {
        if(fetchGroupsObservable == null) {
            fetchGroupsObservable = fetchLightResponses(false)
                    .flatMap(lightResponses -> {
                        List<Observable<Group>> observables = new ArrayList<>();
                        Map<String, Group> groups = new ArrayMap<>();

                        for (LightResponse light : lightResponses) {
                            Group group = light.getGroup();
                            if (!groups.containsKey(group.getId())) {
                                group.addLight(light);
                                groups.put(group.getId(), group);

                                observables.add(Observable.just(group));
                            } else {
                                groups.get(group.getId()).addLight(light);
                            }
                        }

                        return Observable.merge(observables);
                    });
        }

        return fetchGroupsObservable;
    }

    @DebugLog
    @NonNull
    private synchronized Observable<Light> fetchLights() {
        if (fetchLightsObservable == null) {
            fetchLightsObservable = fetchLightResponses(false)
                    .flatMap(lightResponses -> {
                        List<Observable<Light>> observables = new ArrayList<>();

                        for (LightResponse lightResponse : lightResponses) {
                            observables.add(Observable.just(lightResponse));
                        }

                        return Observable.merge(observables);
                    });
        }

        return fetchLightsObservable;
    }

    @DebugLog
    @NonNull
    @Override
    public synchronized Observable<LightNetwork> fetchLightNetwork() {
        fetchLightsObservable = null;
        fetchGroupsObservable = null;
        fetchLocationsObservable = null;

        List<Observable<? extends WifiLightData>> observables = new ArrayList<>();

        observables.add(fetchLocations(true));

        LightNetwork lightNetwork = new LightNetwork();

        return Observable.zip(observables, args -> {
            for (Object object : args) {
                if (object instanceof Location) {
                    lightNetwork.addLightLocation((Location) object);
                }
            }
            return lightNetwork;
        })
        .doOnCompleted(() -> eventBus.postMessage(new FetchLightNetworkEvent(lightNetwork))
                .subscribe(new ErrorSubscriber<>()));
    }

    @DebugLog
    @NonNull
    @Override
    public final Observable<Light> fetchLight(@Nullable final String id) {
        if(id == null) {
            return Observable.error(new IllegalArgumentException("fetchLight() id cannot be null"));
        }

        return fetchLights()
                .filter(light -> light.getId().equals(id));
    }

    @NonNull
    @Override
    public Observable<Group> fetchGroup(String groupId) {
        if(groupId == null) {
            return Observable.error(new IllegalArgumentException("fetchGroup() groupId cannot be null"));
        }

        return fetchGroups()
                .filter(group -> group.getId().equals(groupId));

    }

    @NonNull
    @Override
    public Observable<Location> fetchLocation(String locationId) {
        if(locationId == null) {
            return Observable.error(new IllegalArgumentException("fetchLocation() locationId cannot be null"));
        }

        return fetchLocations(false)
                .filter(location -> location.getId().equals(locationId));
    }

    public void onEvent(LightSelectorChangedEvent event) {
        currentSelector = event.selector();
    }

    @DebugLog
    @NonNull
    private Observable<LightStatus> doSetColour(@NonNull final String colourQuery,
                                                @NonNull final String durationQuery,
                                                @NonNull final LightSelector selector) {
        // TODO colour set power on
        Map<String, String> queryMap = new ArrayMap<>();
        queryMap.put(NetworkConstants.URL_COLOUR, colourQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);
        queryMap.put(NetworkConstants.URL_POWER_ON, "true");

        return lightService.setColour(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                selector.toString(),
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
                        .subscribe(new ServerErrorEventSubscriber<>(eventBus)));
    }

    @NonNull
    private Observable<List<LightResponse>> fetchLightResponses(final boolean fetchFromServer) {
        if(fetchFromServer || lightResponsesObservable == null) {
            lightResponsesObservable = lightService.listLights(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    NetworkConstants.URL_ALL,
                    authorisation())
                    .onErrorResumeNext(throwable -> {
                        return handleNetworkError(throwable);
                    })
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return lightResponsesObservable
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @NonNull
    private Observable handleNetworkError(Throwable throwable) {
        // getLight the response parsing
        if (throwable instanceof RetrofitError) {
            RetrofitError error = (RetrofitError) throwable;
            ErrorResponse response = (ErrorResponse) error.getBody();

            Exception exception;

            switch (error.getResponse().getStatus()) {
                case 401:
                case 404:
                case 408:
                case 422:
                case 426:
                case 429:
                    exception = new WifiLightServerException();
                    break;
                case 500:
                case 502:
                case 503:
                case 523:
                default:
                    exception = new WifiLightAPIException();
                    break;
            }

            eventBus.postMessage((new ErrorEvent(exception)))
                    .subscribe(new ErrorSubscriber<>());

            return Observable.error(exception);
        } else {
            return Observable.error(throwable);
        }
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
