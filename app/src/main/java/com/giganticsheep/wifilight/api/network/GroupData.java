package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;

import org.parceler.Parcel;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Parcel
public class GroupData implements Group, Location {
    public String id;
    public String label;

    @Override
    public String id() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "id='" + id + '\'' +
                ", name='" + label + '\'' +
                '}';
    }
}
