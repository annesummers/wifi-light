package com.giganticsheep.wifilight.ui.base;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public class LocationChangedEvent {
    //private final Location location;
    private final String locationId;

    public LocationChangedEvent(String location) {
        this.locationId = location;
    }

   // public Location getLocation() {
//        return location;
 //   }

    public String getLocationId() {
        return locationId;
    }
}
