package com.giganticsheep.wifilight.model;

import com.giganticsheep.wifilight.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class WifiLightObject {

    @SuppressWarnings("FieldNotUsedInToString")
    protected final Logger logger = new Logger(getClass().getName());

    private final long id;
    private List<String> labels = new ArrayList();

    protected final LightNetwork network;

    /**
     * @param network the network this object is part of
     * @param id the id of this object
     */
    protected WifiLightObject(final LightNetwork network, final String id) {
        this.network = network;
        this.id = Long.getLong(id);
    }

    final long id() {
        return id;
    }

    final List<String> labels() {
        return labels;
    }

    void addLabel(String label) {
        labels.add(label);
    }
}
