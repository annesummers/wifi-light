package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class DetailsPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected LightPresenterBase createPresenter(@NonNull final LightPresenterBase.Injector injector) {
        return new DetailsPresenter(injector);
    }

    @NonNull
    @Override
    protected DetailsPresenter getPresenter() {
        return (DetailsPresenter) presenter;
    }
}