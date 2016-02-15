package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.nofragmentbase.ui.base.ScreenGroup;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Location;

import javax.inject.Inject;

/**
 * Created by anne on 24/11/15.
 */
public class LocationScreen extends Screen<LocationPresenter> {

    @Inject
    LocationPresenter presenter;

    protected LocationScreen(ScreenGroup screenGroup) {
        super(screenGroup);
    }

    protected LocationScreen(Parcel in) {
        super(in);
    }

    @Override
    public LocationPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void injectDependencies() {

    }

    @Override
    protected int layoutId() {
        return R.layout.view_location;
    }

    /**
     * DESCRIPTION HERE ANNE <p>
     * Created by anne on 04/09/15. <p>
     * (*_*)
     */
    public interface LocationViewInterface extends ViewInterfaceBase {
        void showLocation(Location location);
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
