package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightFragmentPresenterBase {

    public LightEffectsPresenter(@NonNull final Injector injector,
                                 @NonNull final LightControlPresenter lightControlPresenter) {
        super(injector, lightControlPresenter);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<LightEffectsPresenter>(logger));
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this).subscribe(new ErrorSubscriber<LightEffectsPresenter>(logger));
    }

    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
