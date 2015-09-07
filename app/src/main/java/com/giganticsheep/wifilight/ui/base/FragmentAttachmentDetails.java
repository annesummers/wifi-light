package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.parcelableplease.ParcelBagger;
import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Map;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
@ParcelablePlease
public class FragmentAttachmentDetails implements Parcelable {
    String name;
    int position;
    boolean addToBackStack;
    int extra;

    @Bagger(FragmentAttachmentDetails.FragmentArgsMapBagger.class)
    Map<String, String> argsMap = new ArrayMap<>();

    public FragmentAttachmentDetails(final String name,
                                     final int position) {
        this.name = name;
        this.extra = Constants.INVALID;
        this.position = position;
        this.addToBackStack = false;
    }

    public FragmentAttachmentDetails(final String name,
                                     final int extra,
                                     final int position) {
        this.name = name;
        this.extra = extra;
        this.position = position;
        this.addToBackStack = false;
    }

    public FragmentAttachmentDetails() { }

    public void addStringArg(final String argName, final String arg) {
        argsMap.put(argName, arg);
    }

    @NonNull
    @Override
    public String toString() {
        return "FragmentAttachmentDetails{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", addToBackStack=" + addToBackStack +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        FragmentAttachmentDetailsParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<FragmentAttachmentDetails> CREATOR = new Creator<FragmentAttachmentDetails>() {
        public FragmentAttachmentDetails createFromParcel(Parcel source) {
            FragmentAttachmentDetails target = new FragmentAttachmentDetails();
            FragmentAttachmentDetailsParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public FragmentAttachmentDetails[] newArray(int size) {
            return new FragmentAttachmentDetails[size];
        }
    };

    public static class FragmentArgsMapBagger implements ParcelBagger<Map<String, String>> {

        @Override
        public void write(Map<String, String> map, Parcel out, int flags) {
            out.writeInt(map.size());
            for(Map.Entry<String, String> entry : map.entrySet()){
                out.writeString(entry.getKey());
                out.writeString(entry.getValue());
            }
        }

        @Override
        public Map<String, String> read(final Parcel in) {
            int size = in.readInt();
            Map<String, String> map = new ArrayMap<>(size);
            for(int i = 0; i < size; i++){
                String key = in.readString();
                String value = in.readString();
                map.put(key,value);
            }

            return map;
        }
    }
}
