package com.giganticsheep.wifilight.ui.navigation;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.GroupChangedEvent;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 07/09/15. <p>
 * (*_*)
 */
public class NavigationPresenter extends PresenterBase<NavigationView> {

    public NavigationPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @DebugLog
    final public void fetchLightNetwork() {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLightNetwork(), new Subscriber<LightNetwork>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                eventBus.postMessage(new ErrorEvent(e));
            }

            @Override
            public void onNext(LightNetwork lightNetwork) {
                eventBus.postMessage(new LightControl.FetchLightNetworkEvent(lightNetwork));
            }
        });
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
    @DebugLog
    public void onEventMainThread(@NonNull final LocationChangedEvent event) {
        getView().showLocation(event.getLocationId());
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Group} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Group}.
     */
    @DebugLog
    public void onEventMainThread(@NonNull final GroupChangedEvent event) {
        getView().showGroup(event.getGroupId());
    }

    public void groupChanged(String groupId) {
        eventBus.postMessageSticky(new GroupChangedEvent(groupId));
    }

    public void lightChanged(String lightId) {
        eventBus.postMessageSticky(new LightChangedEvent(lightId));
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
         * @param locationPresenter the class to inject.
         */
        void inject(final NavigationPresenter locationPresenter);
    }
}
