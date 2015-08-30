package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.base.ErrorEvent;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.base.GroupChangedEvent;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkPresenter extends MvpBasePresenter<LightNetworkView> {

    @NonNull
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Inject EventBus eventBus;
    @Inject LightControl lightControl;

    private int locationPosition;
    private int groupPosition;
    private int lightPosition;

    public LightNetworkPresenter(@NonNull final Injector injector) {
        injector.inject(this);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @DebugLog
    public void onDestroy() {
        compositeSubscription.unsubscribe();

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    /**
     * Fetches the Light with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param id the id of the Light to fetch.
     */
    @DebugLog
    public void fetchLight(final String id) {
        subscribe(lightControl.fetchLight(id), new Subscriber<Light>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(Light light) {
                subscribe(eventBus.postMessage(new LightChangedEvent(light)));
            }
        });
    }

    /**
     * Fetches the Group with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param groupId the id of the group to fetch.
     */
    public void fetchGroup(final String groupId) {
        subscribe(lightControl.fetchGroup(groupId), new Subscriber<Group>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(Group group) {
                subscribe(eventBus.postMessage(new GroupChangedEvent(group)));
            }
        });
    }

    /**
     * Fetches the Location with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param locationId the id of the group to fetch.
     */
    public void fetchLocation(final String locationId) {
        subscribe(lightControl.fetchLocation(locationId), new Subscriber<Location>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(Location location) {
                subscribe(eventBus.postMessage(new LocationChangedEvent(location)));
            }
        });
    }

    public void setPosition(final int locationPosition,
                            final int groupPosition,
                            final int lightPosition) {
        this.locationPosition = locationPosition;
        this.groupPosition = groupPosition;
        this.lightPosition = lightPosition;
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsEvent
     */
    @DebugLog
    public synchronized void onEvent(@NonNull LightControl.FetchLightNetworkEvent event) {
        getView().showLightNetwork(event.lightNetwork(), locationPosition, groupPosition, lightPosition);
    }

    @DebugLog
    public synchronized void onEvent(@NonNull ErrorEvent event) {
        getView().showError(event.getError());
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     */
    private <T> void subscribe(@NonNull final Observable<T> observable,
                               @NonNull final Subscriber<T> subscriber) {
        compositeSubscription.add(observable.subscribe(subscriber));
    }

    /**
     * Subscribes to observable with ErrorSubscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param <T> the type the Observable is observing
     */
    private <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>());
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param presenter the class to inject.
         */
        void inject(final LightNetworkPresenter presenter);
    }

    static class ListItemData {
        private final String id;
        private final String label;

        public ListItemData(final String id,
                            final String label) {
            this.id = id;
            this.label = label;
        }

        public final String getId() {
            return id;
        }

        public final String getLabel() {
            return label;
        }
    }

}