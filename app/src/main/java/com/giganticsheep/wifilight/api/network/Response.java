package com.giganticsheep.wifilight.api.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@ParcelablePlease
class Response implements Parcelable {

    @SerializedName("cod")
    public int httpCode;

    public String id;
    public String label;

    protected Response(String id) {
        this.id = id;
    }

    public Response() { }

    public final String getId() {
        return id;
    }

    public final String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "LightResponse{" +
                "httpCode=" + httpCode +
                ", id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ResponseParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        public Response createFromParcel(Parcel source) {
            Response target = new Response();
            ResponseParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Response[] newArray(int size) {
            return new Response[size];
        }
    };
}
