package com.giganticsheep.wifilight.api.network;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@ParcelablePlease
public class LocationImpl implements Location, Parcelable {

    LightCollectionData data;

    public ArrayList<Group> groups = new ArrayList<>();

    public LocationImpl() { }

    public LocationImpl(@NonNull final String locationId,
                        @NonNull final String locationName) {
        data = new LightCollectionData(locationId, locationName);
    }

    @Override
    public final String getId() {
        return data.id;
    }

    @Override
    public final String getName() {
        return data.name;
    }

    @Override
    public void addGroup(@NonNull final Group group) {
        groups.add(group);
    }

    @Override
    public Group getGroup(@NonNull final String groupId) {
        for(Group g : groups) {
            if(groupId.equals(g.getId())) {
                return g;
            }
        }

        return null;
    }

    @Override
    public final Group getGroup(final int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public final boolean containsGroup(@NonNull final String groupId) {
        for(Group g : groups) {
            if(groupId.equals(g.getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final int groupCount() {
        return groups.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LocationImplParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<LocationImpl> CREATOR = new Creator<LocationImpl>() {
        public LocationImpl createFromParcel(Parcel source) {
            LocationImpl target = new LocationImpl();
            LocationImplParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public LocationImpl[] newArray(int size) {
            return new LocationImpl[size];
        }
    };
}
