package com.giganticsheep.wifilight.api.network;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 27/08/15. <p>
 * (*_*)
 */
@ParcelablePlease
class LightCollectionData implements Parcelable {

    String id;
    String name;

    public LightCollectionData(@NonNull final String groupId,
                               @NonNull final String groupName) {
        id = groupId;
        name = groupName;
    }

    public LightCollectionData() { }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LightCollectionDataParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<LightCollectionData> CREATOR = new Creator<LightCollectionData>() {
        public LightCollectionData createFromParcel(Parcel source) {
            LightCollectionData target = new LightCollectionData();
            LightCollectionDataParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public LightCollectionData[] newArray(int size) {
            return new LightCollectionData[size];
        }
    };
}
