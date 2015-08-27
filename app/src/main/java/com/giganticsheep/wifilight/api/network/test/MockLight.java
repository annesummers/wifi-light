package com.giganticsheep.wifilight.api.network.test;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.network.GroupImpl;
import com.giganticsheep.wifilight.api.network.LocationImpl;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
@ParcelablePlease
public class MockLight extends MockLightBase
                        implements Light, Parcelable {

    public boolean connected;

    @NonNull
    public String power;

    public double brightness;

    @NonNull
    public ColourData color;

    public GroupImpl group;
    public LocationImpl location;
    public long seconds_since_seen;

    public MockLight() {
        super();
    }

    public MockLight(@NonNull final String id,
                     @NonNull final String label) {
        super(id, label);

        this.color = new ColourData();
        this.power = LightControl.Power.OFF.getPowerString();
        this.group = new GroupImpl(null, null);
        this.location = new LocationImpl(null, null);
    }

    public MockLight(@NonNull final String id,
                     @NonNull final String label,
                     @NonNull final String locationId,
                     @NonNull final String groupId) {
        super(id, label);

        this.color = new ColourData();
        this.power = LightControl.Power.OFF.getPowerString();
        this.group = new GroupImpl(groupId, null);
        this.location = new LocationImpl(locationId, null);
    }

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
        return LightControl.Power.parse(power);
    }

    @NonNull
    @Override
    public Date getLastSeen() {
        return new Date();
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return seconds_since_seen;
    }

    @NonNull
    @Override
    public String getProductName() {
        return "Test";
    }

    @Override
    public boolean hasColour() {
        return true;
    }

    @Override
    public boolean hasVariableColourTemp() {
        return true;
    }

    @Override
    public String getLocationId() {
        return location.getId();
    }

    @Override
    public String getGroupId() {
        return group.getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        MockLightParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<MockLight> CREATOR = new Creator<MockLight>() {
        public MockLight createFromParcel(Parcel source) {
            MockLight target = new MockLight();
            MockLightParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public MockLight[] newArray(int size) {
            return new MockLight[size];
        }
    };
}
