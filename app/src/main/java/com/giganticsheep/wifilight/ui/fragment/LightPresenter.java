package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public abstract class LightPresenter extends MvpBasePresenter<LightView> {
    protected final EventBus eventBus;
    protected LightNetwork lightNetwork;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Light>() {
                    @Override
                    public void onCompleted() {
                        if (isViewAttached()) {
                            getView().showLightDetails();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(!(e instanceof IllegalArgumentException)) {
                            if (isViewAttached()) {
                                getView().showError(e);
                            }
                        }

                        // if it is an IllegalArgumentException then it is beacuse
                        // the id is null which happens when the initial set of lights data
                        // has not yet been fully received
                    }

                    @Override
                    public void onNext(Light light) {
                        getView().lightChanged(light);
                    }
                });
    }

    public abstract void fragmentDestroyed();
}
