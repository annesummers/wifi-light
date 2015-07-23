package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class DetailsPresenterTest extends FragmentPresenterTestBase {

    @NonNull
    @Override
    protected FragmentPresenterBase doCreatePresenter(@NonNull final LightPresenterBase.Injector injector,
                                                      @NonNull final ControlPresenter controlPresenter) {
        return new DetailsPresenter(injector, controlPresenter);
    }

    @NonNull
    @Override
    protected DetailsPresenter getPresenter() {
        return (DetailsPresenter) presenter;
    }
}