package com.giganticsheep.wifilight.mvp.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public interface ViewBase extends MvpView {

    /**
     * Show the loading view.
     */
    void showLoading();

    /**
     * Show the error view for an unspecified error.
     */
    void showError();

    /**
     * Show the error view for the Throwable.
     *
     * @param throwable the Throwable specifying the error.
     */
    void showError(Throwable throwable);
}
