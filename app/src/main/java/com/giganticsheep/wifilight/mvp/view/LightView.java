package com.giganticsheep.wifilight.mvp.view;

import com.giganticsheep.wifilight.api.model.Light;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by anne on 29/06/15.
 * (*_*)
 *
 * Interface implemented by UI elements for the Presenters to control the UI
 */
public interface LightView extends MvpView {

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

    /**
     * Show the main view
     */
    void showMainView();

    /**
     * Called when new Light information has be received.
     *
     * @param light the new Light details.
     */
    void lightChanged(Light light);
}
