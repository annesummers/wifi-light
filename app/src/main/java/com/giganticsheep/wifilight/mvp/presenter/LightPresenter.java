package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rx.Subscriber;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightPresenter extends MvpBasePresenter<LightView> {
    protected final EventBus eventBus;
    protected LightNetwork lightNetwork;

    protected LightSubscriber lightSubscriber = new LightSubscriber();

    public LightPresenter(LightNetwork lightNetwork,
                          EventBus eventBus) {
        this.lightNetwork = lightNetwork;
        this.eventBus = eventBus;
    }

    public void fetchLight(String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        lightNetwork.fetchLight(id)
                .subscribe(lightSubscriber);
    }

    public void onDestroy() { }

    private class LightSubscriber extends Subscriber<Light>  {

        @Override
        public void onCompleted() {
            if (isViewAttached()) {
                getView().showLightDetails();
            }
        }

        @Override
        public void onError(Throwable e) {
            if (isViewAttached()) {
                getView().showError(e);
            }
        }

        @Override
        public void onNext(Light light) {
            getView().lightChanged(light);
        }
    }
}
