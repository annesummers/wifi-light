package com.giganticsheep.wifilight.api.model;

import android.os.Parcelable;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public interface Group extends Parcelable {

    String getId();

    /**
     *
     * @return the name of this Group
     */
    String getName();

    void addLight(Light light);

    int lightCount();

    Light getLight(int lightPosition);
}
