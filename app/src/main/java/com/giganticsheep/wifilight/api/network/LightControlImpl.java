package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.giganticsheep.wifilight.ApplicationScope;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.api.network.error.WifiLightAPIException;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.base.error.SilentErrorSubscriber;
import com.giganticsheep.wifilight.base.error.WifiLightException;
import com.giganticsheep.wifilight.api.network.error.WifiLightNetworkException;
import com.giganticsheep.wifilight.api.network.error.WifiLightServerException;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.dagger.IOScheduler;
import com.giganticsheep.wifilight.base.dagger.UIScheduler;
import com.giganticsheep.wifilight.base.error.ErrorStrings;

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
public class LightControlImpl extends LightControlEventCatcher
                                implements LightControl {

    private final ErrorSubscriber errorSubscriber;

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
                            @NonNull final ErrorStrings errorStrings,
                            @IOScheduler final Scheduler ioScheduler,
                            @UIScheduler final Scheduler uiScheduler) {
        this.lightService = lightService;
        this.networkDetails = networkDetails;
        this.eventBus = eventBus;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;

        this.errorSubscriber = new ErrorSubscriber(eventBus, errorStrings);

        eventBus.registerForEvents(this).subscribe(new SilentErrorSubscriber());
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
        if(currentSelector.getType() == LightSelector.SelectorType.LIGHT) {
            return lightService.togglePowerSingleLight(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    currentSelector.toString(),
                    authorisation(), "")
                    .map(statusResponse -> (LightStatus) statusResponse)
                    .onErrorResumeNext(throwable -> {
                        return handleNetworkError(throwable);
                    })
                    .doOnCompleted(() -> fetchLightNetwork()
                            .subscribe(errorSubscriber))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler);
        }

        return lightService.togglePower(
                networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                currentSelector.toString(),
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
                        .subscribe(errorSubscriber))
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

        Map<String, String> queryMap = new ArrayMap<>();
        queryMap.put(NetworkConstants.URL_STATE, powerQuery);
        queryMap.put(NetworkConstants.URL_DURATION, durationQuery);

        if(currentSelector.getType() == LightSelector.SelectorType.LIGHT) {
            return lightService.setPowerSingleLight(
                    networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    currentSelector.toString(),
                    authorisation(),
                    queryMap)
                    .map(statusResponse -> (LightStatus) statusResponse)
                    .onErrorResumeNext(throwable -> {
                        return handleNetworkError(throwable);
                    })
                    .doOnCompleted(() -> fetchLightNetwork()
                            .subscribe(errorSubscriber))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler);
        }

        return lightService.setPower(
                networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                currentSelector.toString(),
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
                        .subscribe(errorSubscriber))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    @DebugLog
    @NonNull
    private synchronized Observable<Location> fetchLocations(final boolean fetchFromServer) {
        if(fetchFromServer || fetchLocationsObservable == null) {
            fetchLocationsObservable = fetchLightResponses(fetchFromServer)
                    .flatMap(lightResponses -> {
                        List<Observable<Location>> observables = new ArrayList<>();
                        Map<String, Location> locations = new ArrayMap<>();

                        for (LightResponse light : lightResponses) {
                            Timber.d(light.toString());

                            LightCollectionData locationData = light.getLocation();
                            LightCollectionData groupData = light.getGroup();

                            Location location = null;
                            boolean createNewLocation = !locations.containsKey(locationData.id);

                            if (createNewLocation) {
                                location = new LocationImpl(locationData.id, locationData.name);
                            } else {
                                location = locations.get(locationData.id);
                            }

                            boolean createGroup = !location.containsGroup(groupData.id);

                            Group group = null;
                            if(createGroup) {
                                group = new GroupImpl(groupData.id, groupData.name);
                            } else {
                                group = location.getGroup(groupData.id);
                            }

                            group.addLight(light);

                            if(createGroup) {
                                location.addGroup(group);
                            }

                            if (createNewLocation) {
                                locations.put(locationData.id, location);

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
            fetchGroupsObservable = fetchLocations(false)
                    .flatMap(locations -> {
                        List<Observable<Group>> observables = new ArrayList<>();

                        for(int i = 0; i < locations.groupCount(); i++) {
                            Group group = locations.getGroup(i);

                            observables.add(Observable.just(group));
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
            fetchLightsObservable = fetchGroups()
                    .flatMap(groups -> {
                        List<Observable<Light>> observables = new ArrayList<>();

                        for(int i = 0; i < groups.lightCount(); i++) {
                            Light light = groups.getLight(i);

                            observables.add(Observable.just(light));
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

        List<Observable<Location>> observables = new ArrayList<>();

        observables.add(fetchLocations(true));

        LightNetwork lightNetwork = new LightNetwork();

        return Observable.zip(observables, args -> {
            for (Object object : args) {
                lightNetwork.addLightLocation((Location) object);
            }

            return lightNetwork;
        })
        .doOnCompleted(() -> eventBus.postMessage(new FetchLightNetworkEvent(lightNetwork))
                .subscribe(errorSubscriber));
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

    @Override
    void setCurrentSelector(final LightSelector selector) {
        currentSelector = selector;
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

        if(selector.getType() == LightSelector.SelectorType.LIGHT) {
            return lightService.setColourSingleLight(networkDetails.getBaseURL1(),
                    networkDetails.getBaseURL2(),
                    selector.toString(),
                    authorisation(),
                    queryMap)
                    .map(statusResponse -> (LightStatus) statusResponse)
                    .onErrorResumeNext(throwable -> {
                        return handleNetworkError(throwable);
                    })
                    .doOnCompleted(() -> fetchLightNetwork()
                            .subscribe(errorSubscriber))
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler);
        }

        return lightService.setColour(networkDetails.getBaseURL1(),
                networkDetails.getBaseURL2(),
                selector.toString(),
                authorisation(),
                queryMap)
                .flatMap(statusResponses -> {
                    List<Observable<LightStatus>> observables = new ArrayList<>(statusResponses.size());

                    for (LightStatus lightStatus : statusResponses) {
                        Timber.d(lightStatus.toString());
                    }

                    return Observable.merge(observables);
                })
                .onErrorResumeNext(throwable -> {
                    return handleNetworkError(throwable);
                })
                .doOnCompleted(() -> fetchLightNetwork()
                        .subscribe(errorSubscriber))
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
                    .onErrorResumeNext(throwable -> {
                       return handleNetworkError(throwable);
                    })
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .cache();
        }

        return lightResponsesObservable;
    }

    @NonNull
    private Observable handleNetworkError(Throwable throwable) {
        if (throwable instanceof RetrofitError) {
            RetrofitError error = (RetrofitError) throwable;

            Exception exception = null;
            switch(error.getKind()) {
                case NETWORK:
                    exception = new WifiLightNetworkException(error.getMessage());
                    break;
                case HTTP:
                    ErrorResponse response = (ErrorResponse) error.getBodyAs(ErrorResponse.class);
                    switch (error.getResponse().getStatus()) {
                        case 401:
                        case 404:
                        case 408:
                        case 422:
                        case 426:
                        case 429:
                            exception = new WifiLightAPIException(error.getResponse().getReason(), response);
                            break;
                        case 500:
                        case 502:
                        case 503:
                        case 523:
                        default:
                            exception = new WifiLightServerException(error.getResponse().getReason(), response);
                            break;
                    }
                    break;
                case CONVERSION:
                case UNEXPECTED:
                default:
                    exception = new WifiLightException();
                    break;
            }

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
