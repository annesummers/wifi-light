package com.giganticsheep.wifilight.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.model.Light;
import com.giganticsheep.wifilight.model.LightNetwork;
import com.giganticsheep.wifilight.model.ModelConstants;
import com.giganticsheep.wifilight.ui.rx.RXFragment;
import com.squareup.otto.Subscribe;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
public class HSVFragment extends RXFragment {

    private static final String CURRENT_LIGHT_ARGUMENT = "current_light";

    private Light light;

    public static HSVFragment newInstance(String name) {
        HSVFragment fragment = new HSVFragment();

        Bundle args = new Bundle();
        args.putString(RXFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    public HSVFragment() {
        super();
    }

    private SeekBar hueSeekBar;
    private SeekBar saturationSeekBar;
    private SeekBar valueSeekBar;
    private SeekBar kelvinSeekBar;

    private ToggleButton powerToggle;

    private TextView nameTextView;
    private TextView idTextView;
    private TextView hueTextView;
    private TextView saturationTextView;
    private TextView brightnessTextView;
    private TextView kelvinTextView;

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    @Override
    public void onStop() {
        super.onStop();

        logger.debug("onStop()");

        getArguments().putSerializable(CURRENT_LIGHT_ARGUMENT, light);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        logger.debug("onViewStateRestored()");

        if(light == null) {
            Bundle mySavedInstanceState = getArguments();
            light = (Light) mySavedInstanceState.getSerializable(CURRENT_LIGHT_ARGUMENT);

            if(light == null) {
                getMainActivity().fetchLights();
            }
        }

        if(light != null) {
            setLightDetails();
        }
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getRXActivity();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_hsv;
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return true;
    }

    @Override
    protected void initialiseViews(View view) {
        logger.debug("initialiseViews()");

        hueSeekBar = (SeekBar) view.findViewById(R.id.hue_seekbar);
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        saturationSeekBar = (SeekBar) view.findViewById(R.id.saturation_seekbar);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        valueSeekBar = (SeekBar) view.findViewById(R.id.brightness_seekbar);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        kelvinSeekBar = (SeekBar) view.findViewById(R.id.kelvin_seekbar);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        nameTextView = (TextView) view.findViewById(R.id.name_textview);
        idTextView = (TextView) view.findViewById(R.id.id_textview);
        hueTextView = (TextView) view.findViewById(R.id.hue_textview);
        saturationTextView = (TextView) view.findViewById(R.id.saturation_textview);
        brightnessTextView = (TextView) view.findViewById(R.id.brightness_textview);
        kelvinTextView = (TextView) view.findViewById(R.id.kelvin_textview);

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

        WifiLightApplication.application().registerForEvents(this);

        if(light != null) {
            setLightDetails();
        }
    }

    @Override
    protected void destroyViews() {
        logger.debug("destroyViews()");

        WifiLightApplication.application().unregisterForEvents(this);
    }

    @Subscribe
    public void lightChange(LightNetwork.LightDetailsEvent event) {
        logger.debug("lightChange()");

        light = event.light();
        setLightDetails();
    }
    
    private void setLightDetails() {
        int hue = (int)light.getHue();
        int saturation = (int)(light.getSaturation()*100);
        int brightness = (int)(light.getBrightness()*100);
        int kelvin = (int) light.getKelvin();

        nameTextView.setText(light.getName());
        idTextView.setText(light.id());
        hueTextView.setText(Integer.toString(hue));
        saturationTextView.setText(Integer.toString(saturation));
        brightnessTextView.setText(Integer.toString(brightness));
        kelvinTextView.setText(Integer.toString(kelvin));

        hueSeekBar.setProgress(hue);
        saturationSeekBar.setProgress(saturation);
        valueSeekBar.setProgress(brightness);
        kelvinSeekBar.setProgress(kelvin - 2500);

        powerToggle.setChecked(light.getPower() == ModelConstants.Power.ON);
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
