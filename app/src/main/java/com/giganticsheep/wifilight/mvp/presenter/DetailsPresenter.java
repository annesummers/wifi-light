package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class DetailsPresenter extends FragmentPresenterBase {

    public DetailsPresenter(@NonNull final Injector injector,
                            @NonNull final ControlPresenter controlPresenter) {
        super(injector, controlPresenter);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<DetailsPresenter>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<DetailsPresenter>());
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
