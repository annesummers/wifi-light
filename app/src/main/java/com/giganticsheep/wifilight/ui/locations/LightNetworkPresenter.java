package com.giganticsheep.wifilight.ui.locations;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetworkPresenter extends PresenterBase<LightNetworkView> {

    private int locationPosition;

    public LightNetworkPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    public void locationChanged(final String locationId) {
        eventBus.postMessage(new LocationChangedEvent(locationId));
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsEvent
     */
    public synchronized void onEventMainThread(@NonNull final LightControl.FetchLightNetworkEvent event) {
        getView().showLightNetwork(event.lightNetwork(), locationPosition);
    }

    //@DebugLog
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
