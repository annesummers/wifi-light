package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@Parcel
public class GroupData implements Group {
    public String id;
    public String name;

    public ArrayList<Light> lights = new ArrayList<>();

    public GroupData(String groupId, String groupName) {
        id = groupId;
        name = groupName;
    }

    public GroupData() { }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addLight(final Light light) {
        lights.add(light);
    }

    @Override
    public int lightCount() {
        return lights.size();
    }

    @Override
    public Light getLight(int lightPosition) {
        return lights.get(lightPosition);
    }
}
