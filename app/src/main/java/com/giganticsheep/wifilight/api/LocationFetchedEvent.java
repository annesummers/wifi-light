package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.api.model.Location;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class LocationFetchedEvent {
    private final Location location;

    public LocationFetchedEvent(Location location) {
        this.location = location;
    }

    public final Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "FetchedLocationEvent{" +
                "location=" + location +
                '}';
    }
}
