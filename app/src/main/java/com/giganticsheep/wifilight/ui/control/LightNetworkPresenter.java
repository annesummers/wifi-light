package com.giganticsheep.wifilight.ui.control;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.FetchLightNetworkEvent;
import com.giganticsheep.wifilight.api.FetchedGroupEvent;
import com.giganticsheep.wifilight.api.FetchedLightEvent;
import com.giganticsheep.wifilight.api.FetchedLocationEvent;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.ui.ErrorEvent;
import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import org.jetbrains.annotations.Nullable;

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

    @Nullable
    private LightNetwork lightNetwork = null;

    private int groupPosition;
    private int childPosition;

    private LightNetwork network;

    public LightNetworkPresenter(@NonNull final Injector injector) {
        injector.inject(this);

        lightNetwork = null;

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

    @DebugLog
     public synchronized void onEvent(@NonNull FetchedLocationEvent event) {
        lightNetwork = new LightNetwork();
        lightNetwork.add(event.getLocation());
    }

    /**
     * Called every time a {@link com.giganticsheep.wifilight.api.model.Light} has been fetched from the network.
     *
     * @param event contains the fetched {@link com.giganticsheep.wifilight.api.model.Light}.
     */
  //  @DebugLog
    public synchronized void onEvent(@NonNull FetchedGroupEvent event) {
        if(lightNetwork != null) {
            lightNetwork.add(event.getGroup());
        }
    }

    /**
     * Called every time a {@link com.giganticsheep.wifilight.api.model.Light} has been fetched from the network.
     *
     * @param event contains the fetched {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @DebugLog
    public synchronized void onEvent(@NonNull FetchedLightEvent event) {
        if(lightNetwork != null) {
            Light light = event.getLight();

            if(lightNetwork.lightExists(light.getGroup().id(), light.id())) {
                lightNetwork.remove(light.getGroup().id(), light.id());
            }

            lightNetwork.add(new LightViewData(light.id(),
                    light.getLabel(),
                    light.isConnected(),
                    light.getGroup().id()));
        }
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsEvent
     */
    @DebugLog
    public synchronized void onEvent(@NonNull FetchLightNetworkEvent event) {
        if(lightNetwork != null) {
            getView().showLightNetwork(lightNetwork, groupPosition, childPosition);
        }

        lightNetwork = null;
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

    public void setPosition(int groupPosition, int childPosition) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
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

    protected static class ListItemData {
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
