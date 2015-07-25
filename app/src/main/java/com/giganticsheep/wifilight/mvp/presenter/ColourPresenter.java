package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

/**
 * Presenter to handle the setting of the selected Lights' colours and brightness.<p>
 *
 * Created by anne on 29/06/15.<p>
 *
 * (*_*)
 */
public class ColourPresenter extends BrightnessPresenterBase {

    public ColourPresenter(@NonNull final Injector injector) {
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
     * Sets the hue of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param hue the hue to set; an int between 0 and 360.
     * @param duration the duration to fade into the new hue.
     */
    public void setHue(final int hue, float duration) {
        subscribe(lightControl.setHue(hue, duration), new SetLightSubscriber());
    }

    /**
     * Sets the saturation of the selected {@link com.giganticsheep.wifilight.api.model.Light}s.
     *
     * @param saturation the saturation to set; an int between 0 and 100.
     * @param duration the duration to fade into the new saturation.
     */
    public void setSaturation(final int saturation, float duration) {
        subscribe(lightControl.setSaturation(saturation, duration), new SetLightSubscriber());
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
