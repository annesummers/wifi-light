package com.giganticsheep.wifilight.api.model;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public interface Group extends WifiLightData {

    /**
     *
     * @return the name of this Group
     */
    String getName();

    /*
     * The list of the Lights in this Group
     */
    //List<Light> getLights();

    void addLight(Light light);

    int lightCount();

    Light getLight(int lightPosition);

    // Location getLightLocation();
}
