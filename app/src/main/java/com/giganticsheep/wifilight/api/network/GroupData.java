package com.giganticsheep.wifilight.api.network;

import com.giganticsheep.wifilight.api.model.Group;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
class GroupData implements Group {
    public String id;
    public String name;

    @Override
    public String id() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
