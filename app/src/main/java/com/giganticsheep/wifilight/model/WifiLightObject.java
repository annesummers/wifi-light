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
    protected final Logger mLogger = new Logger(getClass().getName());

    private final String mId;
    private List<String> mLabels = new ArrayList();

    protected final LightNetwork mNetwork;

    /**
     * @param network the network this object is part of
     * @param id the id of this object
     */
    protected WifiLightObject(final LightNetwork network, final String id) {
        mNetwork = network;
        mId = id;
    }

    final String id() {
        return  mId;
    }

    final List<String> labels() {
        return mLabels;
    }

    void addLabel(String label) {
        mLabels.add(label);
    }
}
