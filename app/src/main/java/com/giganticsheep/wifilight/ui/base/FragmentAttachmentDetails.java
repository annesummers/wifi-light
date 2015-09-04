package com.giganticsheep.wifilight.ui.base;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.util.Constants;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

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

   /* private FragmentAttachmentDetails(@NonNull final Parcel in) {
        name = in.readString();
        extra = in.readInt();
        position = in.readInt();
        addToBackStack = in.readByte() != 0;
    }

    public static final Creator<FragmentAttachmentDetails> CREATOR = new Creator<FragmentAttachmentDetails>() {
        @NonNull
        @Override
        public FragmentAttachmentDetails createFromParcel(@NonNull final Parcel source) {
            return new FragmentAttachmentDetails(source);
        }

        @NonNull
        @Override
        public FragmentAttachmentDetails[] newArray(final int size) {
            return new FragmentAttachmentDetails[size];
        }
    };

    public String name() {
        return name;
    }

    public int position() {
        return position;
    }

      @Override
    public void writeToParcel(@NonNull final Parcel parcel, final int flags) {
        parcel.writeString(name);
        parcel.writeInt(position);
        parcel.writeByte((byte) (addToBackStack ? 1 : 0));
    }

    public boolean addToBackStack() {
        return addToBackStack;
    }*/

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

    @NonNull
    @Override
    public String toString() {
        return "FragmentAttachmentDetails{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", addToBackStack=" + addToBackStack +
                '}';
    }
}
