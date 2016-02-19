package com.giganticsheep.wifilight.ui.base;

import android.content.Context;
import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.FlowActivity;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.LightNetwork;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.error.ErrorEvent;
import com.giganticsheep.wifilight.base.error.ErrorStrings;

import javax.inject.Inject;

/**
 * Created by anne on 19/02/16.
 */
public class WifiLightScreenGroup extends ScreenGroup {

    @Inject protected ErrorStrings errorStrings;
    @Inject protected EventBus eventBus;
    @Inject protected LightControl lightControl;
    @Inject protected Context context;

    public void fetchLightNetwork() {
        subscribe(lightControl.fetchLightNetwork(), new LightNetworkSubscriber(this));
    }

    private static class LightNetworkSubscriber extends ScreenGroupSubscriber<LightNetwork> {

        public LightNetworkSubscriber(WifiLightScreenGroup screenGroup) {
            super(screenGroup);
        }

        @Override
        public void onCompleted() { }

        @Override
        public void onError(Throwable e) {
            WifiLightScreenGroup screenGroup = (WifiLightScreenGroup) screenGroupWeakReference.get();
            if(screenGroup != null) {
                screenGroup.eventBus.postMessage(new ErrorEvent(e));
                screenGroup.postControlEvent(new FlowActivity.ShowErrorEvent(e));
            }
        }

        @Override
        public void onNext(LightNetwork lightNetwork) {
            WifiLightScreenGroup screenGroup = (WifiLightScreenGroup) screenGroupWeakReference.get();
            if(screenGroup != null) {
                screenGroup.eventBus.postMessageSticky(new LightControl.FetchLightNetworkEvent(lightNetwork));
            }
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) { }
}
