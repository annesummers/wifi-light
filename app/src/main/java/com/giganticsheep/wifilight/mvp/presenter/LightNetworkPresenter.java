package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.FetchGroupsEvent;
import com.giganticsheep.wifilight.api.FetchLightsEvent;
import com.giganticsheep.wifilight.api.FetchLocationsEvent;
import com.giganticsheep.wifilight.api.FetchedGroupEvent;
import com.giganticsheep.wifilight.api.FetchedLightEvent;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.mvp.view.LightNetworkView;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.squareup.otto.Subscribe;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
   // @Inject LightControl lightControl;

    @Nullable
    private LightNetwork lightNetwork = null;

    private int position;
    private LightNetwork network;

    public LightNetworkPresenter(@NonNull final Injector injector) {
        injector.inject(this);

        lightNetwork = new LightNetwork();
    }

    @Override
    public void attachView(LightNetworkView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    public void onDestroy() {
        compositeSubscription.unsubscribe();
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Location}s have been fetched from the network.
     *
     * @param event a FetchLocationsSuccessEvent
     */
    @DebugLog
    @Subscribe
    public synchronized void handleFetchLocationsSuccess(@NonNull FetchLocationsEvent event) {
        lightNetwork = new LightNetwork();
        position = 0;
    }

        /**
         * Called when all the available {@link com.giganticsheep.wifilight.api.model.Group}s have been fetched from the network.
         *
         * @param event a FetchGroupsSuccessEvent
         */
    @DebugLog
    @Subscribe
    public synchronized void handleFetchGroupsSuccess(@NonNull FetchGroupsEvent event) {
        getView().showLightNetwork(lightNetwork);
    }

    /**
     * Called every time a {@link com.giganticsheep.wifilight.api.model.Light} has been fetched from the network.
     *
     * @param event contains the fetched {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @DebugLog
    @Subscribe
    public synchronized void handleGroupDetails(@NonNull FetchedGroupEvent event) {
        if(lightNetwork != null) {
            lightNetwork.add(new GroupViewData(position++, event.getGroup()));
        }
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsSuccessEvent
     */
    @DebugLog
    @Subscribe
    public synchronized void handleFetchLightsSuccess(@NonNull FetchLightsEvent event) {
        getView().showLightNetwork(lightNetwork);
    }

    /**
     * Called every time a {@link com.giganticsheep.wifilight.api.model.Light} has been fetched from the network.
     *
     * @param event contains the fetched {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @DebugLog
    @Subscribe
    public synchronized void handleLightDetails(@NonNull FetchedLightEvent event) {
        if(lightNetwork != null) {
            lightNetwork.add(new LightViewData(position++, event.getLight()));
        }
    }

    /**
     * Subscribes to observable with subscriber, retaining the resulting Subscription so
     * when the Presenter is destroyed the Observable can be unsubscribed from.
     *
     * @param observable the Observable to subscribe to
     * @param subscriber the Subscriber to subscribe with
     * @param <T> the type the Observable is observing
     */
    protected <T> void subscribe(@NonNull final Observable<T> observable,
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
    protected <T> void subscribe(@NonNull final Observable<T> observable) {
        subscribe(observable, new ErrorSubscriber<T>());
    }

    public LightNetwork getNetwork() {
        return network;
    }

    public int getPosition() {
        return position;
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

    public static class LightViewData {
        private final int position;
        private final Light light;

        public LightViewData(final int position,
                             final Light light) {
            this.position = position;
            this.light = light;
        }

        public Light getLight() {
            return light;
        }

        @Override
        public String toString() {
            return "LightViewData{" +
                    "position=" + position +
                    ", getLight=" + light +
                    '}';
        }
    }

    public static class GroupViewData {
        private final int position;
        private final Group group;

        public GroupViewData(final int position,
                             final Group group) {
            this.position = position;
            this.group = group;
        }

        public Group getGroup() {
            return group;
        }

        @Override
        public String toString() {
            return "GroupViewData{" +
                    "position=" + position +
                    ", group=" + group +
                    '}';
        }
    }

    /**
     * DESCRIPTION HERE ANNE <p>
     * Created by anne on 24/07/15. <p>
     * (*_*)
     */
    public static class LightNetwork {

        private final List<LightViewData> lightDataList = new ArrayList<>();
        private final HashMap<GroupViewData, List<LightViewData>> groupDataMap = new HashMap<>();

        public void clear() {
            lightDataList.clear();
            groupDataMap.clear();
        }

        public void add(GroupViewData group) {
            List<LightViewData> subList = new ArrayList<>();

            for(LightViewData light : lightDataList) {
                if(light.getLight().getGroup().id().equals(group.getGroup().id())) {
                    subList.add(light);
                }

                groupDataMap.put(group, subList);
            }
        }

        public void add(LightViewData light) {
            lightDataList.add(light);

            List<LightViewData> subList;

            for(GroupViewData group : groupDataMap.keySet()) {
                if (group.getGroup().id().equals(light.getLight().id())) {
                    subList = groupDataMap.get(group);
                    subList.add(light);

                    groupDataMap.put(group, subList);

                    break;
                }
            }
        }

        public int size() {
            return lightDataList.size();
        }

        public LightViewData get(final int position) {
            return lightDataList.get(position);
        }
    }
}
