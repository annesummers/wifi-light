package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class EffectsPresenter extends FragmentPresenterBase {

    public EffectsPresenter(@NonNull final Injector injector,
                            @NonNull final ControlPresenter controlPresenter) {
        super(injector, controlPresenter);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<EffectsPresenter>(logger));
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<EffectsPresenter>(logger));
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
