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
public class CapabilitiesData implements Parcelable {
    public boolean has_color;
    public boolean has_variable_color_temp;

    public CapabilitiesData() {
    }

    @NonNull
    @Override
    public String toString() {
        return "CapabilitiesData{" +
                "has_color=" + has_color +
                ", has_variable_color_temp=" + has_variable_color_temp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        CapabilitiesDataParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<CapabilitiesData> CREATOR = new Creator<CapabilitiesData>() {
        public CapabilitiesData createFromParcel(Parcel source) {
            CapabilitiesData target = new CapabilitiesData();
            CapabilitiesDataParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public CapabilitiesData[] newArray(int size) {
            return new CapabilitiesData[size];
        }
    };
}
