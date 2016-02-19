package com.giganticsheep.wifilight.ui.control;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ViewStateHandler;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.WifiLightScreen;

/**
 * Created by anne on 19/02/16.
 */
public class LightScreen extends WifiLightScreen<LightScreen.ViewAction, LightControlScreenGroup> {

    private Light light;

    public LightScreen(LightControlScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected LightScreen(Parcel in) {
        super(in);
    }

    @Override
    protected void injectDependencies() {
        ((LightControlScreenGroup)getScreenGroup()).getComponent().inject(this);
    }

    @Override
    protected ViewStateHandler createViewState() {
        return new LightViewStateHandler();
    }

    public void fetchLight(String lightId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchLight(lightId), new LightScreenSubscriber(this));
    }

    private static class LightScreenSubscriber extends WifiLightScreenSubscriber<Light> {

        public LightScreenSubscriber(Screen screen) {
            super(screen);
        }

        @Override
        public void onNext(@NonNull final Light light) {
            LightScreen screen = (LightScreen) screenWeakReference.get();
            if(screen != null) {
                screen.light = light;
                screen.setHasData();
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.view_light;
    }

    @Override
    protected void doAction() {
        getView().showLight(light);
    }

    public interface ViewAction extends Screen.ViewActionBase {
        void showLight(Light light);
    }

    public interface Injector {
        void inject(LightScreen lightScreen);
    }

    public static final Parcelable.Creator<LightScreen> CREATOR = new Parcelable.Creator<LightScreen>() {
        public LightScreen createFromParcel(Parcel source) {
            return new LightScreen(source);
        }

        public LightScreen[] newArray(int size) {
            return new LightScreen[size];
        }
    };
}
