package com.giganticsheep.wifilight.api.model;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public interface Location extends WifiLightData {
    /**
     * Get the name of the Location
     *
     * @return the name
     */
    String getName();

    void addGroup(Group group);

    Group getGroup(String groupId);

    Group getGroup(int groupPosition);

    boolean containsGroup(String groupId);

    int groupCount();
}
