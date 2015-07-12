package com.giganticsheep.wifilight.mvp.presenter;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightEffectsPresenter extends LightPresenterBase {

    public LightEffectsPresenter(Injector injector) {
        super(injector);

        eventBus.registerForEvents(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregisterForEvents(this);
    }
}
