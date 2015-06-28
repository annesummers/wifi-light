package com.giganticsheep.wifilight.ui.rx;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */
public class FragmentAttachmentDetails implements Parcelable {
    private final String name;
    private final int position;
    private final boolean addToBackStack;

    public FragmentAttachmentDetails(final String name, final int position, final boolean addToBackStack) {
        this.name = name;
        this.position = position;
        this.addToBackStack = addToBackStack;
    }

    protected FragmentAttachmentDetails(final Parcel in) {
        name = in.readString();
        position = in.readInt();
        addToBackStack = in.readByte() != 0;
    }

    public static final Creator<FragmentAttachmentDetails> CREATOR = new Creator<FragmentAttachmentDetails>() {
        @Override
        public FragmentAttachmentDetails createFromParcel(final Parcel source) {
            return new FragmentAttachmentDetails(source);
        }

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        parcel.writeString(name);
        parcel.writeInt(position);
        parcel.writeByte((byte) (addToBackStack ? 1 : 0));
    }

    public boolean addToBackStack() {
        return addToBackStack;
    }

    @Override
    public String toString() {
        return "FragmentAttachmentDetails{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", addToBackStack=" + addToBackStack +
                '}';
    }
}