package com.giganticsheep.wifilight.api.network;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anne on 26/06/15.
 * (*_*)
 */
@ParcelablePlease
public class LightResponse extends Response
                            implements Light, Parcelable {

    public String uuid;
    public boolean connected;
    public String power;
    public double brightness;
    public String product_name;
    public String last_seen;
    public double seconds_since_seen;

    public ColourData color;
    public LightCollectionData location;
    public LightCollectionData group;
    public CapabilitiesData capabilities;

    public LightResponse() {}

    public LightResponse(String id) {
        super(id);

        color = new ColourData();
        location = new LightCollectionData();
        group = new LightCollectionData();
        capabilities = new CapabilitiesData();
    }

    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ", Locale.US);

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public int getHue() {
        return LightConstants.convertHue(color.hue);
    }

    @Override
    public int getSaturation() {
        return LightConstants.convertSaturation(color.saturation);
    }

    @Override
    public int getKelvin() {
        return color.kelvin;
    }

    @Override
    public int getBrightness() {
        return LightConstants.convertBrightness(brightness);
    }

    @NonNull
    @Override
    public LightControl.Power getPower() {
        return power.equals("on") ? LightControl.Power.ON : LightControl.Power.OFF;
    }

    @Override
    public String getProductName() {
        return product_name;
    }

    @Override
    public Date getLastSeen() {
        Date date = new Date();
        try {
            date = dateFormat.parse(last_seen);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return seconds_since_seen;
    }

    @Override
    public boolean hasColour() {
        return capabilities.has_color;
    }

    @Override
    public boolean hasVariableColourTemp() {
        return capabilities.has_variable_color_temp;
    }

    @Override
    public String getLocationId() {
        return location.id;
    }

    @Override
    public String getGroupId() {
        return group.id;
    }

    LightCollectionData getLocation() {
        return location;
    }

    LightCollectionData getGroup() {
        return group;
    }

    @NonNull
    @Override
    public String toString() {
        return "LightDataResponse{" +
                "uuid='" + uuid + '\'' +
                ", connected=" + connected +
                ", power='" + power + '\'' +
                ", brightness=" + brightness +
                ", product_name='" + product_name + '\'' +
                ", last_seen='" + last_seen + '\'' +
                ", seconds_since_last_seen=" + seconds_since_seen +
                ", color=" + color +
                ", location=" + location +
                ", group=" + group +
                ", capabilities=" + capabilities +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        LightResponseParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<LightResponse> CREATOR = new Creator<LightResponse>() {
        public LightResponse createFromParcel(Parcel source) {
            LightResponse target = new LightResponse();
            LightResponseParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public LightResponse[] newArray(int size) {
            return new LightResponse[size];
        }
    };
}
