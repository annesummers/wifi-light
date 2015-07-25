package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Used by {@link com.giganticsheep.wifilight.ui.LightControlActivity} as an interface between the UI and the model. <p>
 *
 * Created by anne on 09/07/15.<p>
 *
 * (*_*)
 */
public class ControlPresenter extends LightPresenterBase {

    public ControlPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public void attachView(LightView view) {
        super.attachView(view);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<>());
    }

    /**
     * Fetches all the available Lights.
     *
     * @param fetchFromServer whether to fetch the information from the server or to use the
     *                        cached information if we have it.
     */
    public final void fetchLights(boolean fetchFromServer) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        subscribe(lightControl.fetchLights(fetchFromServer), new ErrorSubscriber<>());
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
