package com.giganticsheep.wifilight.ui.base;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 05/08/15. <p>
 * (*_*)
 */
public class GroupChangedEvent {
    private final String groupId;

    public GroupChangedEvent(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
