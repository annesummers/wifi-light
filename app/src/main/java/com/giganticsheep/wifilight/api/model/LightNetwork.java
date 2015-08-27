package com.giganticsheep.wifilight.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a network of lights. <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
@ParcelablePlease
public class LightNetwork implements Parcelable {
     List<Location> locationList = new ArrayList<>();

    /**
     * @param locationPosition
     * @return the number of light groups in this network.
     */
    public int lightGroupCount(final int locationPosition) {
        if(locationPosition == Constants.INVALID ||
                locationPosition > lightLocationCount() - 1) {
            return 0;
        }

        return locationList.get(locationPosition).groupCount();
    }

    /**
     * @param locationPosition
     * @param groupPosition
     * @return the Group at the position groupPosition at the location at locationPosition.
     */
    public final Group getLightGroup(final int locationPosition,
                                     final int groupPosition) {
        if(locationPosition == Constants.INVALID ||
                locationPosition > lightLocationCount() - 1 ||
                groupPosition == Constants.INVALID ||
                groupPosition > lightGroupCount(locationPosition) - 1) {
            throw new IndexOutOfBoundsException();
        }

        return locationList.get(locationPosition).getGroup(groupPosition);
    }

    /**
     * @param locationPosition
     * @return the Location at the given position in this network.
     */
    public final Location getLightLocation(final int locationPosition) {
        if(locationPosition == Constants.INVALID ||
                locationPosition > lightLocationCount()) {
            throw new IndexOutOfBoundsException();
        }


        return locationList.get(locationPosition);
    }

    /**
     * @return the number of light locations in this network.
     */
    public int lightLocationCount() {
        return locationList.size();
    }

    /**
     * @param locationPosition
     * @param groupPosition
     * @return the number of lights in the group at position groupPosition in the location at
     *          locationPosition.
     */
    public int lightCount(final int locationPosition,
                          final int groupPosition) {
        if(locationPosition == Constants.INVALID ||
                locationPosition > lightLocationCount() - 1 ||
                groupPosition == Constants.INVALID ||
                groupPosition > lightGroupCount(locationPosition) - 1) {
            return 0;
        }

        return locationList.get(locationPosition).getGroup(groupPosition).lightCount();
    }

    /**
     * @param locationPosition
     * @param groupPosition
     * @param lightPosition
     * @return the light at the given position in the group at the given position in the location
     *          at the given position.
     */
    public Light getLight(final int locationPosition,
                          final int groupPosition,
                          final int lightPosition) {
        if(locationPosition == Constants.INVALID ||
                locationPosition > lightLocationCount() - 1 ||
                groupPosition == Constants.INVALID ||
                groupPosition > lightGroupCount(locationPosition) - 1 ||
                lightPosition == Constants.INVALID ||
                lightPosition > lightCount(locationPosition, groupPosition) - 1) {
            throw new IndexOutOfBoundsException();
        }

        return locationList.get(locationPosition).getGroup(groupPosition).getLight(lightPosition);
    }

    /**
     * @param location
     */
    public void addLightLocation(@NonNull final Location location) {
        locationList.add(location);
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof LightNetwork)) {
            return false;
        }

        LightNetwork lightNetwork = (LightNetwork) object;

        for(int i = 0; i < lightLocationCount(); i++) {
            for(int j = 0; i < lightGroupCount(i); j++) {
                for(int k = 0; k < lightCount(i, j); k++) {
                    if(!getLight(i, j, k).getId().equals(lightNetwork.getLight(i, j, k).getId())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LightNetworkParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<LightNetwork> CREATOR = new Creator<LightNetwork>() {
        public LightNetwork createFromParcel(Parcel source) {
            LightNetwork target = new LightNetwork();
            LightNetworkParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public LightNetwork[] newArray(int size) {
            return new LightNetwork[size];
        }
    };
}
