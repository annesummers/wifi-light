package com.giganticsheep.wifilight.ui.details;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.ui.base.LightPresenterTestBase;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class DetailsPresenterTest extends LightPresenterTestBase {

    @NonNull
    @Override
    protected DetailsPresenter createPresenter(@NonNull final DetailsPresenter.Injector injector) {
        return new DetailsPresenter(injector);
    }

    @NonNull
    @Override
    protected DetailsPresenter getPresenter() {
        return (DetailsPresenter) presenter;
    }
}