package com.giganticsheep.wifilight.ui.navigation.group;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.ui.base.WifiLightScreen;
import com.giganticsheep.wifilight.ui.navigation.NavigationScreenGroup;

/**
 * Created by anne on 24/11/15.
 */
public class LightGroupScreen extends WifiLightScreen<LightGroupScreen.ViewAction> {

    private Group group;

    public LightGroupScreen(ScreenGroup screenGroup, String groupId) {
        super(screenGroup);
    }

    protected LightGroupScreen(Parcel in) {
        super(in);
    }

    @Override
    protected void injectDependencies() {
        ((NavigationScreenGroup)getScreenGroup()).getComponent().inject(this);
    }

    public void fetchGroup(String groupId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchGroup(groupId),
                new ScreenSubscriber<Group>() {

                    @Override
                    public void onNext(@NonNull final Group group) {
                        LightGroupScreen.this.group = group;
                        setHasData();
                    }
                });
    }

    @Override
    protected int layoutId() {
        return R.layout.view_light_group;
    }

    @Override
    protected void showData() {
        getView().showGroup(group);
    }

    public interface ViewAction extends Screen.ViewActionBase {
        void showGroup(Group group);
    }

    public interface Injector {
        void inject(LightGroupScreen lightGroupScreen);
    }

    public static final Parcelable.Creator<LightGroupScreen> CREATOR = new Parcelable.Creator<LightGroupScreen>() {
        public LightGroupScreen createFromParcel(Parcel source) {
            return new LightGroupScreen(source);
        }

        public LightGroupScreen[] newArray(int size) {
            return new LightGroupScreen[size];
        }
    };
}
