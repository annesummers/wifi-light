package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.api.network.error.WifiLightAPIException;
import com.giganticsheep.wifilight.api.network.error.WifiLightNetworkException;
import com.giganticsheep.wifilight.api.network.error.WifiLightServerException;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorStrings;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;

import javax.inject.Inject;

/**
 * Created by anne on 15/02/16.
 */
public abstract class WifiLightScreen<A extends Screen.ViewActionBase, G extends ScreenGroup>
                                                extends Screen<A, G> {

    @Inject protected EventBus eventBus;
    @Inject protected ErrorStrings errorStrings;
    @Inject public LightControl lightControl;

    private LightSelector selector;

    protected WifiLightScreen(ScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected WifiLightScreen(Parcel in) {
        super(in);
    }

    public void setPower(final boolean isOn) {
        // TODO select group
        if(isOn) {
            subscribe(lightControl.setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber(this));
        } else if(!isOn){
            subscribe(lightControl.setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION),
                    new SetLightSubscriber(this));
        }
    }

    public static class SetLightSubscriber extends WifiLightScreenSubscriber<LightStatus> {

        public SetLightSubscriber(WifiLightScreen screen) {
            super(screen);
        }

        @Override
        public void onNext(@NonNull final LightStatus light) {
            WifiLightScreen screen = (WifiLightScreen) screenWeakReference.get();
            if(screen != null) {
                screen.eventBus.postMessage(new LightChangedEvent((light.getId())));
            }
        }
    }

    public void onAllLightsClick() {
        /*Intent intent = new Intent();
        intent.setClass(getActivity(), LightControlActivity.class);
        intent.putExtra(ActivityBase.ANIMATION_EXTRA, ActivityBase.ANIMATION_FADE);

        getActivity().overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);*/
    }

    protected static abstract class WifiLightScreenSubscriber<O> extends ScreenSubscriber<O> {

        public WifiLightScreenSubscriber(Screen screen) {
            super(screen);
        }

        @Override
        public void onCompleted() {
            Screen screen = screenWeakReference.get();
            if(screen != null) {
                screen.getViewState().setStateShowing();
            }
        }

        @Override
        public void onError(@NonNull final Throwable e) {
            WifiLightScreen screen = (WifiLightScreen) screenWeakReference.get();
            if(screen != null) {
                screen.getViewState().setStateError(transformError(screen.errorStrings, e));
            }
        }

        private Throwable transformError(ErrorStrings errorStrings, Throwable error) {
            StringBuilder errorString = new StringBuilder();

            if(error instanceof WifiLightAPIException) {
                errorString.append(errorStrings.apiErrorString());
                errorString.append('\n');
                errorString.append(error.getMessage());
            } else if(error instanceof WifiLightServerException) {
                errorString.append(errorStrings.serverErrorString());

                if(BuildConfig.DEBUG) {
                    errorString.append('\n');
                    errorString.append(error.getMessage());
                }
            } else if(error instanceof WifiLightNetworkException) {
                errorString.append(errorStrings.networkErrorString());

                if(BuildConfig.DEBUG) {
                    errorString.append('\n');
                    errorString.append(error.getMessage());
                }
            } else {
                errorString.append(errorStrings.generalErrorString());
                errorString.append('\n');
                errorString.append(error.getMessage());
            }

            return new Exception(errorString.toString());
        }
    }
}
