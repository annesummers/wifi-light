package com.giganticsheep.wifilight.util;

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

import java.util.Map;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 07/09/15. <p>
 * (*_*)
 */
public class MapBagger implements ParcelBagger<Map<String, Object>> {
    @Override
    public void write(Map<String, Object> value, Parcel out, int flags) {

    }

    @Override
    public Map<String, Object> read(Parcel in) {
        return null;
    }
}
