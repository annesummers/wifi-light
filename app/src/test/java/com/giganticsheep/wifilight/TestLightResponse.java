package com.giganticsheep.wifilight;

import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.ColourData;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;

import java.util.Date;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
public class TestLightResponse implements Light {
    private final String id;
    public double brightness;
    public ColourData color;

    public TestLightResponse(String testId) {
        this.id = testId;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean isConnected() {
        return false;
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

    @Override
    public LightControl.Power getPower() {
        return LightControl.Power.OFF;
    }

    @Override
    public Date getLastSeen() {
        return null;
    }

    @Override
    public double getSecondsSinceLastSeen() {
        return 0;
    }

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
