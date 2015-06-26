package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.model.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class LightColourFragment extends LightFragment {

    public static LightColourFragment newInstance(String name) {
        LightColourFragment fragment = new LightColourFragment();

        Bundle args = new Bundle();
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public LightColourFragment() {
        super();
    }

    private SeekBar hueSeekBar;
    private SeekBar saturationSeekBar;
    private SeekBar valueSeekBar;
    private SeekBar kelvinSeekBar;

    private ToggleButton powerToggle;

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    @Override
    protected int layoutId() {
        return R.layout.fragment_colour;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected void initialiseData(Bundle savedInstanceState) {
        super.initialiseData(savedInstanceState);

        WifiLightApplication.application().registerForEvents(this);
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");

        // TODO sliders thumb drops off the end
        // TODO put toggle above pager
        // TODO colour of tabs

        hueSeekBar = (SeekBar) view.findViewById(R.id.hue_seekbar);
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        saturationSeekBar = (SeekBar) view.findViewById(R.id.saturation_seekbar);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        valueSeekBar = (SeekBar) view.findViewById(R.id.brightness_seekbar);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        kelvinSeekBar = (SeekBar) view.findViewById(R.id.kelvin_seekbar);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        powerToggle = (ToggleButton) view.findViewById((R.id.power_toggle));
        powerToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked && light != null && light.getPower() != ModelConstants.Power.ON) {
                    getMainActivity().setPower(ModelConstants.Power.ON);
                } else if(!isChecked && light != null && light.getPower() != ModelConstants.Power.OFF){
                    getMainActivity().setPower(ModelConstants.Power.OFF);
                }
            }
        });
    }

    @Override
    protected void setLightDetails() {
        int hue = (int)light.getHue();
        int saturation = (int)(light.getSaturation()*100);
        int brightness = (int)(light.getBrightness()*100);
        int kelvin = (int) light.getKelvin();

        hueSeekBar.setProgress(hue);
        saturationSeekBar.setProgress(saturation);
        valueSeekBar.setProgress(brightness);
        kelvinSeekBar.setProgress(kelvin - 2500);

        powerToggle.setChecked(light.getPower() == ModelConstants.Power.ON);
    }

    @Override
    protected void destroyViews() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        WifiLightApplication.application().unregisterForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        logger.debug("lightChange()");

        handleLightChange(event.light());
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                if (seekBar.equals(hueSeekBar)) {
                    getMainActivity().setHue(value);
                    return;
                }

                if (seekBar.equals(saturationSeekBar)) {
                    getMainActivity().setSaturation(value);
                    return;
                }

                if (seekBar.equals(valueSeekBar)) {
                    getMainActivity().setBrightness(value);
                    return;
                }

                if (seekBar.equals(kelvinSeekBar)) {
                    getMainActivity().setKelvin(value);
                    return;
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    }
}
