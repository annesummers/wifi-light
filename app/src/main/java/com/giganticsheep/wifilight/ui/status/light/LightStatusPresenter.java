package com.giganticsheep.wifilight.ui.status.light;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.status.StatusPresenterBase;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 08/09/15. <p>
 * (*_*)
 */
public class LightStatusPresenter extends StatusPresenterBase<LightStatusView> {
    /**
     * Constructs the StatusPresenter object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    public LightStatusPresenter(@NonNull final Injector injector) {
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
                if (isViewAttached()) {
                    getView().showLight(light);
                }
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
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling a LightFragmentBase derived class to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(LightStatusPresenter fragmentBase);
    }
}
