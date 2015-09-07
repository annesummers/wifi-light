package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;
import com.giganticsheep.wifilight.util.Constants;

import hugo.weaving.DebugLog;
import rx.Subscriber;

/**
 * Base class for all the Presenters that show information about a Light.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
public abstract class LightPresenterBase extends PresenterBase<LightView> {

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightPresenterBase(@NonNull final Injector injector) {
        injector.inject(this);
    }

    /**
     * Fetches the Light with the given getId.  Subscribes to the model's method using
     * the Subscriber given.
     *
     * @param id the id of the Light to fetch.
     */
    @DebugLog
    public void fetchLight(final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLight(id), new Subscriber<Light>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                eventBus.postMessage(new ErrorEvent(e));
            }

            @Override
            public void onNext(Light light) {
                eventBus.postMessage(new LightChangedEvent(light));
            }
        });
    }

    /**
     * Called to provide common functionality for when the {@link com.giganticsheep.wifilight.api.model.Light}
     * has changed.  Sets the {@link com.giganticsheep.wifilight.api.model.Light}
     * in the associated view and calls to the view to show the correct screen depending on the
     * status of the {@link com.giganticsheep.wifilight.api.model.Light}.
     *
     * @param light the new Light.
     */
    @DebugLog
    public void handleLightChanged(@NonNull final Light light) {
        if (isViewAttached()) {
            if (light.isConnected()) {
                if (light.getSecondsSinceLastSeen() > Constants.LAST_SEEN_TIMEOUT_SECONDS) {
                    getView().showConnecting(light);
                } else {
                    getView().showConnected(light);
                }
            } else {
                getView().showDisconnected(light);
            }
        }
    }

    /**
     * Called to provide common functionality for when the {@link com.giganticsheep.wifilight.api.model.Group}
     * has changed.  Sets the {@link com.giganticsheep.wifilight.api.model.Group}
     * in the associated view and calls to the view to show the correct screen depending on the
     * status of the {@link com.giganticsheep.wifilight.api.model.Group}.
     *
     * @param group the new group.
     */
    @DebugLog
    public void handleGroupChanged(@NonNull final Group group) {
        // TODO show group
    }

    /**
     * Called to provide common functionality for when the {@link com.giganticsheep.wifilight.api.model.Location}
     * has changed.  Sets the @link com.giganticsheep.wifilight.api.model.Location}
     * in the associated view and calls to the view to show the correct screen depending on the
     * status of the @link com.giganticsheep.wifilight.api.model.Location}.
     *
     * @param location the new location.
     */
    @DebugLog
    public void handleLocationChanged(@NonNull final Location location) {
        // TODO show location
    }

    @DebugLog
    public void handleError(Throwable error) {
        if (isViewAttached()) {
            getView().showError(error);
        }
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Light} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @DebugLog
    public void onEvent(@NonNull final LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Group} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Group}.
     */
  /*  @DebugLog
    public void onEvent(@NonNull final GroupChangedEvent event) {
        handleGroupChanged(event.getGroup());
    }*/

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
   /* @DebugLog
    public void onEvent(@NonNull final LocationChangedEvent event) {
        handleLocationChanged(event.getLocation());
    }*/

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightPresenterBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        /**
         * Injects the lightPresenter class into the Component implementing this interface.
         *
         * @param lightPresenter the class to inject.
         */
        void inject(final LightPresenterBase lightPresenter);
    }

    public class SetLightSubscriber extends ErrorSubscriber<LightStatus> {

        public SetLightSubscriber() {
            super(eventBus, errorStrings);
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            fetchLight(light.getId());
        }
    }
}
