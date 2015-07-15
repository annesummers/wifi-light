package com.giganticsheep.wifilight.mvp.presenter;

import android.support.annotation.NonNull;

/**
 * Created by anne on 10/07/15.
 * (*_*)
 */
public class LightDetailsPresenterTest extends LightFragmentPresenterTestBase {

    @NonNull
    @Override
    protected LightFragmentPresenter doCreatePresenter(LightPresenterBase.Injector injector,
                                                       LightControlPresenter lightControlPresenter) {
        return new LightDetailsPresenter(injector, lightControlPresenter);
    }

    @NonNull
    @Override
    protected LightDetailsPresenter getPresenter() {
        return (LightDetailsPresenter) presenter;
    }
}