package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.model.Light;

import rx.Subscriber;

/**
 * Created by anne on 14/07/15.
 * (*_*)
 */
public abstract class FragmentPresenterBase extends LightPresenterBase {

    private final ControlPresenter controlPresenter;

    /**
     * Constructs the LightPresenterBase object.  Injects itself into the supplied Injector.
     *
     * @param injector an Injector used to inject this object into a Component that will
     *                 provide the injected class members.
     * @param controlPresenter the Presenter that handles the details of the current Light.
     */
    protected FragmentPresenterBase(@NonNull final Injector injector,
                                    @NonNull final ControlPresenter controlPresenter) {
        super(injector);

        this.controlPresenter = controlPresenter;
    }

    @Nullable
    public Light getLight() {
        return controlPresenter.getLight();
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
