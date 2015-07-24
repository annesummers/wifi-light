package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.api.model.Location;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class MockLight extends MockLightBase
                        implements Light {

    public boolean connected;

    @NonNull
    public LightControl.Power power;

    public double brightness;

    @NonNull
    public ColourData color;

    public GroupData group;
    public GroupData location;

    public MockLight(@NonNull final String id,
                     @NonNull final String label) {
        super(id, label);

        this.color = new ColourData();
        this.power = LightControl.Power.OFF;
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
        return power;
    }

    @NonNull
    @Override
    public Date getLastSeen() {
        return new Date();
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return 0;
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
    public Location getLocation() {
        return location;
    }

    @Override
    public Group getGroup() {
        return group;
    }
}
