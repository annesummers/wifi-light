package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.api.model.Location;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 23/08/15. <p>
 * (*_*)
 */
public class LocationChangedEvent {
    private final Location location;

    public LocationChangedEvent(Location location) {
        this.location = location;
    }
}
