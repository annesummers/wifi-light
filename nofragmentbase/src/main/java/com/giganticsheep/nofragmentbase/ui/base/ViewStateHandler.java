package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anne on 28/10/15.
 */
public class ViewStateHandler implements ViewState, Parcelable {

    public final static int STATE_SHOW_LOADING = 0;
    public final static int STATE_SHOWING = 1;
    public final static int STATE_SHOW_ERROR = 2;

    public final static int STATE_MAX = STATE_SHOW_ERROR;

    protected int state;

    private Throwable error;

    protected ViewState view;

    public ViewStateHandler() {
        state = STATE_SHOW_LOADING;
    }

    public ViewStateHandler(Parcel in) {
        state = in.readInt();
        error = (Throwable) in.readSerializable();
    }

    protected void apply() {
        if(view != null) {
            switch (state) {
                case STATE_SHOW_LOADING:
                    view.setStateLoading();
                    break;
                case STATE_SHOWING:
                    view.setStateShowing();
                    break;
                case STATE_SHOW_ERROR:
                    view.setStateError(error);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void setStateError(String error) {
        setShowError(new Throwable(error));

        if(view != null) {
            view.setStateError(error);
        }
    }

    @Override
    public void setStateError(Throwable throwable) {
        setShowError(throwable);

        if(view != null) {
            view.setStateError(throwable);
        }
    }

    @Override
    public void setStateLoading() {
        setShowLoading();

        if(view != null) {
            view.setStateLoading();
        }
    }

    @Override
    public void setStateShowing() {
        setShowing();

        if(view != null) {
            view.setStateShowing();
        }
    }

    void attachView(ViewState view) {
        this.view = view;
        apply();
    }

    void detachView(ViewState view) {
        this.view = null;
    }

    private void setShowLoading() {
        state = STATE_SHOW_LOADING;
    }

    private void setShowing() {
        state = STATE_SHOWING;
    }

    private void setShowError(final Throwable throwable) {
        state = STATE_SHOW_ERROR;

        this.error = throwable;
    }

    // Parcelling

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(state);
        dest.writeSerializable(error);
    }

    public static final Creator<ViewStateHandler> CREATOR = new Creator<ViewStateHandler>() {
        public ViewStateHandler createFromParcel(Parcel source) {
            return new ViewStateHandler(source);
        }

        public ViewStateHandler[] newArray(int size) {
            return new ViewStateHandler[size];
        }
    };
}
