package com.giganticsheep.wifilight.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */

@ParcelablePlease
public class ColourData implements Parcelable {
    public double hue;
    public double saturation;
    public int kelvin;

    @NonNull
    @Override
    public String toString() {
        return "ColorData{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", kelvin=" + kelvin +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ColourDataParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<ColourData> CREATOR = new Creator<ColourData>() {
        public ColourData createFromParcel(Parcel source) {
            ColourData target = new ColourData();
            ColourDataParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public ColourData[] newArray(int size) {
            return new ColourData[size];
        }
    };
}
