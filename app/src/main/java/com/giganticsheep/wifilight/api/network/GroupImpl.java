package com.giganticsheep.wifilight.api.network;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@ParcelablePlease
public class GroupImpl implements Group, Parcelable {

    LightCollectionData data;

    public ArrayList<Light> lights = new ArrayList<>();

    public GroupImpl() { }

    public GroupImpl(@NonNull final String groupId,
                     @NonNull final String groupName) {
        data = new LightCollectionData(groupId, groupName);
    }

    @Override
    public String getId() {
        return data.id;
    }

    @Override
    public String getName() {
        return data.name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        GroupImplParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<GroupImpl> CREATOR = new Creator<GroupImpl>() {
        public GroupImpl createFromParcel(Parcel source) {
            GroupImpl target = new GroupImpl();
            GroupImplParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public GroupImpl[] newArray(int size) {
            return new GroupImpl[size];
        }
    };

}
