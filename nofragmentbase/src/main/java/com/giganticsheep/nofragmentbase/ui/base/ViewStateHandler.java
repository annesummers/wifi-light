package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 28/10/15.
 */
public class ViewStateHandler implements ViewStateInterface, Parcelable {

    public final static int STATE_SHOW_LOADING = 0;
    public final static int STATE_SHOWING = 1;
    public final static int STATE_SHOW_ERROR = 2;

    private int state;

    private Throwable error;

    private final List<ViewStateInterface> views = new ArrayList<>();

    public ViewStateHandler() {
        state = STATE_SHOW_LOADING;
    }

    public ViewStateHandler(Parcel in) {
        state = in.readInt();
        error = (Throwable) in.readSerializable();
    }

    private void apply() {
        switch (state) {
            case STATE_SHOW_LOADING:
                for(ViewStateInterface view : views) {
                    view.setStateLoading();
                }
                break;
            case STATE_SHOWING:
                for(ViewStateInterface view : views) {
                    view.setStateShowing();
                }
                break;
            case STATE_SHOW_ERROR:
                for(ViewStateInterface view : views) {
                    view.setStateError(error);
                }
                break;
            default:
                break;
        }
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

    @Override
    public void setStateError(String error) {
        setShowError(new Throwable(error));

        for(ViewStateInterface view : views) {
            view.setStateError(error);
        }
    }

    @Override
    public void setStateError(Throwable throwable) {
        setShowError(throwable);

        for(ViewStateInterface view : views) {
            view.setStateError(throwable);
        }
    }

    @Override
    public void setStateLoading() {
        setShowLoading();

        for(ViewStateInterface view : views) {
            view.setStateLoading();
        }
    }

    @Override
    public void setStateShowing() {
        setShowing();

        for(ViewStateInterface view : views) {
            view.setStateShowing();
        }
    }

    void addView(ViewStateInterface view) {
        views.add(view);

        apply();
    }

    void removeView(ViewStateInterface view) {
        views.remove(view);

        apply();
    }

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
