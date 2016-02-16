package com.giganticsheep.nofragmentbase.ui.base;

/**
 * Created by anne on 28/10/15.
 */
public interface ViewState {
    void setStateError(String error);
    void setStateError(Throwable throwable);
    void setStateLoading();
    void setStateShowing();
}
