package com.giganticsheep.wifilight.ui.base;

import com.giganticsheep.wifilight.api.model.Group;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 05/08/15. <p>
 * (*_*)
 */
public class GroupChangedEvent {
    private final Group group;

    public GroupChangedEvent(Group group) {
        this.group = group;
    }
}
