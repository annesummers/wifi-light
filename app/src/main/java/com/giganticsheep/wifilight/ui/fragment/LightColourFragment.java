package com.giganticsheep.wifilight.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.ModelConstants;
import com.giganticsheep.wifilight.ui.MainActivity;
import com.giganticsheep.wifilight.ui.base.BaseFragment;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightColourFragment extends LightFragment {

    public static LightColourFragment newInstance(String name) {
        LightColourFragment fragment = new LightColourFragment();

        Bundle args = new Bundle();
        args.putString(BaseFragment.FRAGMENT_ARGS_NAME, name);
        fragment.setArguments(args);

        return fragment;
    }

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;
    @InjectView(R.id.kelvin_seekbar) SeekBar kelvinSeekBar;
    @InjectView(R.id.power_toggle) ToggleButton powerToggle;

    public LightColourFragment() {
        super();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    public LightPresenter createPresenter() {
        return new LightColourPresenter(lightNetwork, eventBus);
    }

    @Override
    public LightColourPresenter getPresenter() {
        return (LightColourPresenter) super.getPresenter();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_colour;
    }

    @Override
    protected void initialiseViews(View view) {
        // TODO sliders thumb drops off the end
        // TODO put toggle above pager
        // TODO colour of tabs

        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    public void setLightDetails() {
        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        valueSeekBar.setProgress(light.getBrightness());
        kelvinSeekBar.setProgress(light.getKelvin() - 2500);

        powerToggle.setChecked(light.getPower() == ModelConstants.Power.ON);
    }

    @OnCheckedChanged(R.id.power_toggle) public void onPowerToggle(CompoundButton compoundButton,
                                                                   boolean isChecked) {
        if(isChecked && light != null && light.getPower() != ModelConstants.Power.ON) {
            getPresenter().setPower(ModelConstants.Power.ON, MainActivity.DEFAULT_DURATION);
        } else if(!isChecked && light != null && light.getPower() != ModelConstants.Power.OFF){
            getPresenter().setPower(ModelConstants.Power.OFF, MainActivity.DEFAULT_DURATION);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().fragmentDestroyed();
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        private boolean tracking = false;

        private final Map<SeekBar, Integer> values = new HashMap<>();

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                values.put(seekBar, value);
            }
        }

        @Override
        public synchronized void onStartTrackingTouch(SeekBar seekBar) {
            tracking = true;
        }

        @Override
        public synchronized void onStopTrackingTouch(SeekBar seekBar) {
            tracking = false;

            if(values.containsKey(seekBar)) {
                int value = values.get(seekBar);

                if (seekBar.equals(hueSeekBar)) {
                    getPresenter().setHue(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(saturationSeekBar)) {
                    getPresenter().setSaturation(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(valueSeekBar)) {
                    getPresenter().setBrightness(value, MainActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(kelvinSeekBar)) {
                    getPresenter().setKelvin(value, MainActivity.DEFAULT_DURATION);
                }

                values.remove(seekBar);
            }
        }
    }
}
