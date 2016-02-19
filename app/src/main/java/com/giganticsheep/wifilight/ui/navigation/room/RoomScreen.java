package com.giganticsheep.wifilight.ui.navigation.room;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.LightStatus;
import com.giganticsheep.wifilight.ui.base.LightChangedEvent;
import com.giganticsheep.wifilight.ui.base.WifiLightScreen;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.navigation.NavigationScreenGroup;

/**
 * Created by anne on 24/11/15.
 */
public class RoomScreen extends WifiLightScreen<RoomScreen.ViewAction> {

    private Group group;

    public RoomScreen(ScreenGroup screenGroup) {
        super(screenGroup);

        setInAnimation(0);
        setOutAnimation(0);
    }

    protected RoomScreen(Parcel in) {
        super(in);
    }

    @Override
    protected void injectDependencies() {
        ((NavigationScreenGroup)getScreenGroup()).getComponent().inject(this);
    }

    public void fetchGroup(String groupId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchGroup(groupId), new RoomScreenSubscriber(this));
    }

    private static class RoomScreenSubscriber extends WifiLightScreenSubscriber<Group> {

        public RoomScreenSubscriber(Screen screen) {
            super(screen);
        }

        @Override
        public void onNext(@NonNull final Group group) {
            RoomScreen screen = (RoomScreen) screenWeakReference.get();
            if(screen != null) {
                screen.group = group;
                screen.setHasData();
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.view_room;
    }

    @Override
    protected void showData() {
        getView().showGroup(group);
    }

    public interface ViewAction extends Screen.ViewActionBase {
        void showGroup(Group group);
    }

    public interface Injector {
        void inject(RoomScreen roomScreen);
    }

    public static final Parcelable.Creator<RoomScreen> CREATOR = new Parcelable.Creator<RoomScreen>() {
        public RoomScreen createFromParcel(Parcel source) {
            return new RoomScreen(source);
        }

        public RoomScreen[] newArray(int size) {
            return new RoomScreen[size];
        }
    };
}
