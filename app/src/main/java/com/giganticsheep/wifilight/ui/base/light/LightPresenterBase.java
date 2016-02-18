package com.giganticsheep.wifilight.ui.base.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.base.error.ErrorSubscriber;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.PresenterBase;
import com.giganticsheep.wifilight.util.Constants;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import timber.log.Timber;

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
    //@DebugLog
    public void fetchLight(final String id) {
        subscribe(lightControl.fetchLight(id), new Subscriber<Light>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                Timber.e("fetchLight", e);
            }

            @Override
            public void onNext(Light light) {
                handleLightChanged(light);
            }
        });
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Location} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Location}.
     */
    //@DebugLog
    public void onEventMainThread(@NonNull final LightChangedEvent event) {
        fetchLight(event.getLightId());
    }

    /**
     * Called to provide common functionality for when the {@link com.giganticsheep.wifilight.api.model.Light}
     * has changed.  Sets the {@link com.giganticsheep.wifilight.api.model.Light}
     * in the associated view and calls to the view to show the correct screen depending on the
     * status of the {@link com.giganticsheep.wifilight.api.model.Light}.
     *
     * @param light the new Light.
     */
    //@DebugLog
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

    //@DebugLog
    public void handleError(Throwable error) {
        if (isViewAttached()) {
            getView().showError(error);
        }
    }

    //@DebugLog
    public void lightChanged(@NonNull final String lightId) {
        eventBus.postMessage(new LightChangedEvent(lightId));
    }

    public class SetLightSubscriber extends ErrorSubscriber<LightStatus> {

        public SetLightSubscriber() {
            super(eventBus, errorStrings);
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            lightChanged(light.getId());
        }
    }

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightPresenterBase fragmentBase);
    }
}
