package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Location;
import com.giganticsheep.wifilight.ui.base.WifiLightScreen;
import com.giganticsheep.wifilight.ui.navigation.NavigationScreenGroup;

/**
 * Created by anne on 24/11/15.
 */
public class LocationScreen extends WifiLightScreen<LocationScreen.LocationViewAction> {

    private Location location;

    public LocationScreen(ScreenGroup screenGroup) {
        super(screenGroup);

        setInAnimation(0);
        setOutAnimation(0);

        getViewState().setStateLoading();
    }

    protected LocationScreen(Parcel in) {
        super(in);
    }

    @Override
    protected void injectDependencies() {
        ((NavigationScreenGroup)getScreenGroup()).getComponent().inject(this);
    }

    public void fetchLocation(String locationId) {
        getViewState().setStateLoading();

        subscribe(lightControl.fetchLocation(locationId), new RoomScreenSubscriber(this));
    }

    private static class RoomScreenSubscriber extends WifiLightScreenSubscriber<Location> {

        public RoomScreenSubscriber(Screen screen) {
            super(screen);
        }

        @Override
        public void onNext(@NonNull final Location location) {
            LocationScreen screen = (LocationScreen) screenWeakReference.get();
            if(screen != null) {
                screen.location = location;
                screen.setHasData();
            }
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.view_location;
    }

    @Override
    protected void showData() {
        getView().showLocation(location);
    }

    public interface LocationViewAction extends Screen.ViewActionBase {
        void showLocation(Location location);
    }

    public interface Injector {
        void inject(LocationScreen locationScreen);
    }

    public static final Parcelable.Creator<LocationScreen> CREATOR = new Parcelable.Creator<LocationScreen>() {
        public LocationScreen createFromParcel(Parcel source) {
            return new LocationScreen(source);
        }

        public LocationScreen[] newArray(int size) {
            return new LocationScreen[size];
        }
    };
}
