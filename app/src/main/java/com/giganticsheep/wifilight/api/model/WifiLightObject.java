package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class WifiLightObject implements Serializable {

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger = new Logger(getClass().getName());

    private final String id;
    private List<String> labels = new ArrayList();


   // protected final LightNetwork network;

    /**
     * @param network the network this object is part of
     * @param id the id of this object
     */
    protected WifiLightObject(/*final LightNetwork network,*/ final String id) {
      //  this.network = network;
        this.id = id;
    }

    public final String id() {
        return id;
    }

    final List<String> labels() {
        return labels;
    }

    void addLabel(String label) {
        labels.add(label);
    }
}
