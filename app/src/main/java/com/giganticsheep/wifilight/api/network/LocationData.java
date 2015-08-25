package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Parcel
public class LocationData implements Location {
    public String id;
    public String name;

    public ArrayList<Group> groups = new ArrayList<>();

    public LocationData() {
    }

    public LocationData(String locationId, String locationName) {
        id = locationId;
        name = locationName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addGroup(Group group) {
        groups.add(group);
    }

    @Override
    public Group getGroup(String groupId) {
        for(Group g : groups) {
            if(groupId.equals(g.getId())) {
                return g;
            }
        }

        return null;
    }

    @Override
    public Group getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public boolean containsGroup(String groupId) {
        for(Group g : groups) {
            if(groupId.equals(g.getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int groupCount() {
        return groups.size();
    }
}
