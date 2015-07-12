package com.giganticsheep.wifilight.mvp.presenter;

import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.base.Logger;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public class LightPresenterBase extends MvpBasePresenter<LightView> {

    protected Logger logger;

    @Inject protected BaseLogger baseLogger;
    @Inject protected EventBus eventBus;
    @Inject protected LightNetwork lightNetwork;

    protected LightSubscriber lightSubscriber = new LightSubscriber();

    public LightPresenterBase(Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);
    }

    public void fetchLight(String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        lightNetwork.fetchLight(id)
                .subscribe(lightSubscriber);
    }

    public void onDestroy() { }

    public interface Injector {
        void inject(LightPresenterBase lightPresenter);
    }

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
