package com.giganticsheep.wifilight.api;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class FetchedLightsEvent {
    private final int lightsFetchedCount;

    public FetchedLightsEvent(final int lightsFetchedCount) {
        this.lightsFetchedCount = lightsFetchedCount;
    }

    public final int getLightsFetchedCount() {
        return lightsFetchedCount;
    }

    @Override
    public String toString() {
        return "FetchLightsSuccessEvent{" +
                "lightsFetchedCount=" + lightsFetchedCount +
                '}';
    }
}
