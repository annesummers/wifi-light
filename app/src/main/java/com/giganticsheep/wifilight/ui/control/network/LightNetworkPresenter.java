package com.giganticsheep.wifilight.ui.control.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkPresenter extends PresenterBase<LightNetworkView> {

    private int locationPosition;
    private int groupPosition;
    private int lightPosition;

    @DebugLog
    public LightNetworkPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    /**
     * Fetches the Light with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param id the id of the Light to fetch.
     */
   /* @DebugLog
    public void fetchLight(final String id) {
        subscribe(lightControl.fetchLight(id), new Subscriber<Light>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(@NonNull final Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(@NonNull final Light light) {
                subscribe(eventBus.postMessage(new LightChangedEvent(light)));
            }
        });
    }*/

    /**
     * Fetches the Group with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param groupId the id of the group to fetch.
     */
  /*  @DebugLog
    public void fetchGroup(final String groupId) {
        subscribe(lightControl.fetchGroup(groupId), new Subscriber<Group>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(@NonNull final Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(@NonNull final Group group) {
                subscribe(eventBus.postMessage(new GroupChangedEvent(group)));
            }
        });
    }*/

    /**
     * Fetches the Location with the given id.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param locationId the id of the Location to fetch.
     */
   /* @DebugLog
    public void fetchLocation(final String locationId) {
        subscribe(lightControl.fetchLocation(locationId), new Subscriber<Location>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(@NonNull final Throwable e) {
                subscribe(eventBus.postMessage(new ErrorEvent(e)));
            }

            @Override
            public void onNext(@NonNull final Location location) {
                subscribe(eventBus.postMessage(new LocationChangedEvent(location)));
            }
        });
    }*/

    /*@DebugLog
    public void setPosition(final int locationPosition,
                            final int groupPosition,
                            final int lightPosition) {
        this.locationPosition = locationPosition;
        this.groupPosition = groupPosition;
        this.lightPosition = lightPosition;
    }*/

    public void locationChanged(final String locationId) {
        eventBus.postMessage(new LocationChangedEvent(locationId));
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsEvent
     */
    @DebugLog
    public synchronized void onEvent(@NonNull final LightControl.FetchLightNetworkEvent event) {
        getView().showLightNetwork(event.lightNetwork(), locationPosition, groupPosition, lightPosition);
    }

    @DebugLog
    public void setPosition(final int locationPosition) {
        this.locationPosition = locationPosition;
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
