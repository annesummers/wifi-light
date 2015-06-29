package com.giganticsheep.wifilight.ui.fragment;

import com.giganticsheep.wifilight.api.model.Light;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 */
public interface LightView extends MvpView {
    void showLoading();

    void showError();

    void showError(Throwable throwable);

    void showLightDetails();

    void lightChanged(Light light);
}
