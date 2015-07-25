package com.giganticsheep.wifilight.api;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class FetchGroupsEvent {
    private final int groupsFetchedCount;

    public FetchGroupsEvent(final int count) {
        groupsFetchedCount = count;
    }

    public final int getGroupsFetchedCount() {
        return groupsFetchedCount;
    }

    @Override
    public String toString() {
        return "FetchGroupsSuccessEvent{" +
                "groupsFetchedCount=" + groupsFetchedCount +
                '}';
    }
}
