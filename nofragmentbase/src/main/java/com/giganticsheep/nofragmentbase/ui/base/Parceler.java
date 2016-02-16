package com.giganticsheep.nofragmentbase.ui.base;

import android.os.Parcel;
import android.os.Parcelable;

import flow.StateParceler;

/**
 * Created by anne on 14/11/15.
 */
public class Parceler implements StateParceler {

    @Override
    public Parcelable wrap(Object instance) {
        if(instance instanceof Screen) {
            return new Wrapper((Parcelable) instance);
        }

       return new Wrapper();
    }

    @Override
    public Object unwrap(Parcelable parcelable) {
        return parcelable;
    }

    private static class Wrapper implements Parcelable {
        final Parcelable parcelable;

        Wrapper(Parcelable parcelable) {
            this.parcelable = parcelable;
        }

        Wrapper() {
            this.parcelable = null;
        }

        @Override public int describeContents() {
            return 0;
        }

        @Override public void writeToParcel(Parcel out, int flags) {
            if(parcelable != null) {
                out.writeParcelable(parcelable, flags);
            }
        }

        public static final Parcelable.Creator<Wrapper> CREATOR = new Parcelable.Creator<Wrapper>() {
            @Override public Wrapper createFromParcel(Parcel in) {
                Parcelable parcelable = in.readParcelable(Screen.class.getClassLoader());
                return new Wrapper(parcelable);
            }

            @Override public Wrapper[] newArray(int size) {
                return new Wrapper[size];
            }
        };
    }
}
