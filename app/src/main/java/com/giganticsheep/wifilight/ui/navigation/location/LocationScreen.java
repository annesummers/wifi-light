package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Parcel;
import android.os.Parcelable;

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

        lightControl.fetchLocation(locationId)
                .subscribe(new ScreenSubscriber<Location>() {
                    @Override
                    public void onNext(Location location) {
                        LocationScreen.this.location = location;
                        setHasData();
                    }
                });

        /*subscribe(lightControl.fetchLocation(locationId), new ScreenSubscriber<Location>() {

                    @Override
                    public void onNext(Location location) {
                        LocationScreen.this.location = location;
                        setHasData();
                    }
                });*/
    }

    @Override
    protected int layoutId() {
        return R.layout.view_location;
    }

    @Override
    protected void showData() {
        getView().showLocation(location);
    }

    /**
     * DESCRIPTION HERE ANNE <p>
     * Created by anne on 04/09/15. <p>
     * (*_*)
     */
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
