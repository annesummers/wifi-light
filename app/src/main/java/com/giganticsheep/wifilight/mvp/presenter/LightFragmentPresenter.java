package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Light;

import rx.Subscriber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public abstract class LightFragmentPresenter extends LightPresenterBase {

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     */
    protected LightFragmentPresenter(@NonNull final Injector injector) {
        super(injector);
    }

    @Override
    public final void fetchLight(@NonNull final String id) {
        if (isViewAttached()) {
            getView().showLoading();
        }

        fetchLight(id, new Subscriber<Light>() {

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }

            @Override
            public void onNext(@NonNull final Light light) {
                handleLightChanged(light);
            }
        });
    }
}
