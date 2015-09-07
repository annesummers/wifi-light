package com.giganticsheep.wifilight.ui.navigation.location;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.ui.base.LocationChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationPresenter extends PresenterBase<LocationView> {

    public LocationPresenter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
    @DebugLog
    public void onEventBackgroundThread(@NonNull final LocationChangedEvent event) {
        subscribe(lightControl.fetchLocation(event.getLocationId()
                ).observeOn(AndroidSchedulers.mainThread()),
                new Subscriber<Location>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        eventBus.postMessage(new ErrorEvent(e));
                    }

                    @Override
                    public void onNext(Location location) {
                        getView().showLocation(location);
                    }
                });

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
        void inject(final LocationPresenter locationPresenter);
    }
}
