package com.giganticsheep.wifilight.api.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class TestLight implements Light {
    private final String id;
    public double brightness;
    public ColourData color;
    public boolean connected;

    public TestLight(String testId) {
        this.id = testId;

        this.color = new ColourData();
    }

    @Override
    public String id() {
        return id;
    }

    @NonNull
    @Override
    public String getName() {
        return "test";
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
        return LightControl.Power.OFF;
    }

    @Nullable
    @Override
    public Date getLastSeen() {
        return null;
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return 0;
    }

    @Nullable
    @Override
    public String getProductName() {
        return null;
    }

    @Override
    public boolean hasColour() {
        return false;
    }

    @Override
    public boolean hasVariableColourTemp() {
        return false;
    }
}
