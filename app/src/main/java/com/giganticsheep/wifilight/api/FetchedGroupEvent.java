package com.giganticsheep.wifilight.api;

import com.giganticsheep.wifilight.api.model.Group;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 25/07/15. <p>
 * (*_*)
 */
public class FetchedGroupEvent {
    private final Group group;

    public FetchedGroupEvent(Group group) {
        this.group = group;
    }

    public final Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "FetchedGroupEvent{" +
                "group=" + group +
                '}';
    }
}
