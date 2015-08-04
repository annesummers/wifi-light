package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.Group;

import org.parceler.Parcel;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Parcel
public class GroupData implements Group {
    public String id;
    public String name;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
