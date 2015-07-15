package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Light;

import rx.Subscriber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public abstract class LightFragmentPresenter extends LightPresenterBase {

    private final LightControlPresenter lightControlPresenter;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     * @param lightControlPresenter the Presenter that handles the details of the current Light.
     */
    protected LightFragmentPresenter(@NonNull final Injector injector,
                                     @NonNull final LightControlPresenter lightControlPresenter) {
        super(injector);

        this.lightControlPresenter = lightControlPresenter;
    }

    @Nullable
    public Light getLight() {
        return lightControlPresenter.getLight();
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
