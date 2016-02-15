package com.giganticsheep.wifilight.ui.navigation.location;

import android.os.Parcel;

import com.giganticsheep.nofragmentbase.ui.base.ViewHandler;
import com.giganticsheep.wifilight.api.model.Location;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 04/09/15. <p>
 * (*_*)
 */
public class LocationViewHandler extends ViewHandler<LocationScreen.LocationViewInterface>
                                implements LocationScreen.LocationViewInterface {

    private Location location;

    public LocationViewHandler(Parcel in) {
        in.readParcelable(Location.class.getClassLoader());
    }

    @Override
    protected void showData() {
        for(LocationScreen.LocationViewInterface view : views) {
            view.showLocation(location);
        }
    }

    @Override
    protected LocationScreen.LocationViewInterface getHandlerView() {
        return this;
    }

    @Override
    public void showLocation(Location location) {
        this.location = location;

        setHasData();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
    }

    public static final Creator<LocationViewHandler> CREATOR = new Creator<LocationViewHandler>() {
        public LocationViewHandler createFromParcel(Parcel source) {
            LocationViewHandler target = new LocationViewHandler(source);

            if(target.location != null) {
                target.setHasData();
            }

            return target;
        }

        public LocationViewHandler[] newArray(int size) {
            return new LocationViewHandler[size];
        }
    };
}
