package com.giganticsheep.wifilight.mvp.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Interface implemented by UI elements for the Presenters to control the UI <p>
 *
 * Created by anne on 29/06/15. <p>
 *
 * (*_*)
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
     * Show the connected view.
     */
    void showConnected();

    /**
     * Show the connecting view.
     */
    void showConnecting();

    /**
     * Show the disconnected view.
     */
    void showDisconnected();
}
