package com.giganticsheep.wifilight.ui.control;

import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.ViewStateHandler;

/**
 * Created by anne on 19/02/16.
 */
public class LightViewStateHandler extends ViewStateHandler implements LightViewState {

    public final static int STATE_SHOW_CONNECTING = STATE_MAX + 1;
    public final static int STATE_DISCONNECTED = STATE_MAX + 2;

    public LightViewStateHandler() {
        super();
    }

    public LightViewStateHandler(Parcel in) {
        super(in);
    }

    protected void apply() {
        if(view != null) {
            LightViewState lightView = (LightViewState)view;
            switch (state) {
                case STATE_SHOW_CONNECTING:
                    lightView.setStateConnecting();
                    break;
                case STATE_DISCONNECTED:
                    lightView.setStateDisconnected();
                    break;
                default:
                    super.apply();
            }
        }
    }

    @Override
    public void setStateDisconnected() {
        setShowDisconnected();

        if(view != null) {
            ((LightViewState)view).setStateDisconnected();
        }
    }

    @Override
    public void setStateConnecting() {
        setShowConnecting();

        if(view != null) {
            ((LightViewState)view).setStateConnecting();
        }
    }

    private void setShowDisconnected() {
        state = STATE_DISCONNECTED;
    }

    private void setShowConnecting() {
        state = STATE_SHOW_CONNECTING;
    }

    public static final Creator<ViewStateHandler> CREATOR = new Creator<ViewStateHandler>() {
        public LightViewStateHandler createFromParcel(Parcel source) {
            return new LightViewStateHandler(source);
        }

        public LightViewStateHandler[] newArray(int size) {
            return new LightViewStateHandler[size];
        }
    };
}
