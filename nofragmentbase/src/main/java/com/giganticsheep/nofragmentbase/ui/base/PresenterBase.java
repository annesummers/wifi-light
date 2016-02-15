package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcel;
import android.os.Parcelable;

import javax.inject.Inject;

/**
 * Created by anne on 07/11/15.
 */
public abstract class PresenterBase<V extends Screen.ViewInterfaceBase> implements Parcelable {

    private ViewStateHandler viewState;
    private ViewHandler<V> viewHandler;

    @Inject
    public PresenterBase(ViewStateHandler viewState, ViewHandler<V> viewHandler) {
        this.viewState = viewState;
        this.viewHandler = viewHandler;
    }

    public PresenterBase(Parcel in) {
        in.readParcelable(ViewStateHandler.class.getClassLoader());
        in.readParcelable(ViewHandler.class.getClassLoader());
    }

    public ViewStateInterface getViewState() {
        return viewState;
    }

    public V getView() {
        return viewHandler.getHandlerView();
    }

    void addView(ViewStateInterface viewState, V view) {
        this.viewHandler.addView(view);
        this.viewState.addView(viewState);
    }

    void removeView(ViewStateInterface viewState, V view) {
        this.viewHandler.removeView(view);
        this.viewState.removeView(viewState);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(viewState, flags);
        dest.writeParcelable(viewHandler, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
