package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.ui.dagger.MainActivityComponent;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightPresenter {

    public LightEffectsPresenter(MainActivityComponent component) {
        super(component);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }
}
