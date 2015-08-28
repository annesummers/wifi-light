package com.giganticsheep.wifilight.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class ViewStateBase<T extends ViewBase> implements RestoreableViewState<T> {

    static final String KEY_STATE = "key_state";
    static final String KEY_ERROR = "key_error";

    final public static int STATE_SHOW_LOADING = 0;
    final public static int STATE_SHOW_ERROR = 1;

    protected static final int STATE_MAX = STATE_SHOW_ERROR;

    protected int state = STATE_SHOW_LOADING;

    private Throwable error;

    /**
     * Sets the state to STATE_SHOW_LOADING.
     */
    public void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    /**
     * Sets the state to STATE_SHOW_ERROR.
     */
    public void setShowError() {
        state = STATE_SHOW_ERROR;
    }

    /**
     * Sets the state to STATE_SHOW_ERROR.
     */
    public void setShowError(Throwable error) {
        this.error = error;
        state = STATE_SHOW_ERROR;
    }

    @Override
    public void apply(@NonNull final ViewBase lightView,
                   final boolean retained) {
        switch (state) {
            case STATE_SHOW_LOADING:
                lightView.showLoading();
                break;

            case STATE_SHOW_ERROR:
                if(error != null) {
                    lightView.showError(error);
                } else {
                    lightView.showError();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void saveInstanceState(Bundle bundle) {
        bundle.putInt(KEY_STATE, state);
        bundle.putSerializable(KEY_ERROR, error);
    }

    @Nullable
    @Override
    public RestoreableViewState<T> restoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }

        state = bundle.getInt(KEY_STATE);
        error = (Throwable) bundle.getSerializable(KEY_ERROR);

        return this;
    }
}
