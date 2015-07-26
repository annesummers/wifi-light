package com.giganticsheep.wifilight.ui.white;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.light.LightView;
import com.giganticsheep.wifilight.ui.base.light.BrightnessPresenterBase;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import hugo.weaving.DebugLog;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/07/15. <p>
 * (*_*)
 */
class WhitePresenter extends BrightnessPresenterBase {

    public WhitePresenter(@NonNull final Injector injector) {
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
     * Sets the kelvin (warmth) of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param kelvin the kelvin to set; an int between 2500 and 9000.
     * @param duration the duration to fade into the new kelvin.
     */
    @DebugLog
    public void setKelvin(final int kelvin, float duration) {
        subscribe(lightControl.setKelvin(kelvin, duration), new SetLightSubscriber());
    }

    /**
     * Called with the details of a {@link com.giganticsheep.wifilight.api.model.Light} to display.
     *
     * @param event contains the new {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @Subscribe
    public void handleLightChanged(@NonNull LightChangedEvent event) {
        handleLightChanged(event.getLight());
    }
}
