package com.giganticsheep.wifilight.api;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class FetchLocationsEvent {
    private final int locationsFetchedCount;

    public FetchLocationsEvent(final int count) {
        locationsFetchedCount = count;
    }

    public final int getLocationsFetchedCount() {
        return locationsFetchedCount;
    }

    @Override
    public String toString() {
        return "FetchLocationsSuccessEvent{" +
                "locationsFetchedCount=" + locationsFetchedCount +
                '}';
    }
}