package com.giganticsheep.wifilight.api.network.test;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.network.GroupData;
import com.giganticsheep.wifilight.api.network.LocationData;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class MockLight extends MockLightBase
                        implements Light {

    public boolean connected;

    @NonNull
    public String power;

    public double brightness;

    @NonNull
    public ColourData color;

    public GroupData group;
    public LocationData location;
    public long seconds_since_seen;

    public MockLight(@NonNull final String id,
                     @NonNull final String label) {
        super(id, label);

        this.color = new ColourData();
        this.power = LightControl.Power.OFF.getPowerString();
        this.group = new GroupData(null, null);
        this.location = new LocationData(null, null);
    }

    public MockLight(@NonNull final String id,
                     @NonNull final String label,
                     @NonNull final String locationId,
                     @NonNull final String groupId) {
        super(id, label);

        this.color = new ColourData();
        this.power = LightControl.Power.OFF.getPowerString();
        this.group = new GroupData(groupId, null);
        this.location = new LocationData(locationId, null);
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
}
