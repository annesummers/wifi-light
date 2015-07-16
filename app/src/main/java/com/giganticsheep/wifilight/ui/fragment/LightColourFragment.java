package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.mvp.presenter.LightColourPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.ui.LightControlActivity;
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
public class LightColourFragment extends LightFragmentBase {

    // TODO put toggle above pager maybe in its own fragment?

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;
    @InjectView(R.id.kelvin_seekbar) SeekBar kelvinSeekBar;
    @InjectView(R.id.power_toggle) ToggleButton powerToggle;

    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

    public LightColourFragment() {
        super();
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new LightColourPresenter(getLightControlActivity().getComponent(),
                                        getLightControlActivity().getPresenter());
    }

    @NonNull
    public LightColourPresenter getLightColourPresenter() {
        return (LightColourPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_colour;
    }

    @Override
    protected void initialiseViews(View view) {
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        valueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        kelvinSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    protected void showLight() {
        //logger.debug("showLight()");

        Light light = getPresenter().getLight();

        if(light == null) {
            logger.error("showLight() light is null");
            return;
        }

        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        valueSeekBar.setProgress(light.getBrightness());
        kelvinSeekBar.setProgress(light.getKelvin() - LightConstants.KELVIN_BASE);

        powerToggle.setChecked(light.getPower() == LightControl.Power.ON);
    }

    @Override
    protected void enableViews(boolean enable) {
        //logger.debug("enableViews()");

        hueSeekBar.setEnabled(enable);
        saturationSeekBar.setEnabled(enable);
        valueSeekBar.setEnabled(enable);
        kelvinSeekBar.setEnabled(enable);

        powerToggle.setEnabled(enable);
    }

    @OnCheckedChanged(R.id.power_toggle)
    public void onPowerToggle(CompoundButton compoundButton, boolean isChecked) {
        Light light = getPresenter().getLight();

        if(isChecked && light != null && light.getPower() != LightControl.Power.ON) {
            getLightColourPresenter().setPower(LightControl.Power.ON, LightControlActivity.DEFAULT_DURATION);
        } else if(!isChecked && light != null && light.getPower() != LightControl.Power.OFF){
            getLightColourPresenter().setPower(LightControl.Power.OFF, LightControlActivity.DEFAULT_DURATION);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
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
        public synchronized void onStopTrackingTouch(@NonNull SeekBar seekBar) {
            tracking = false;

            if(values.containsKey(seekBar)) {
                int value = values.get(seekBar);

                if (seekBar.equals(hueSeekBar)) {
                    getLightColourPresenter().setHue(value, LightControlActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(saturationSeekBar)) {
                    getLightColourPresenter().setSaturation(value, LightControlActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(valueSeekBar)) {
                    getLightColourPresenter().setBrightness(value, LightControlActivity.DEFAULT_DURATION);
                } else if (seekBar.equals(kelvinSeekBar)) {
                    getLightColourPresenter().setKelvin(value, LightControlActivity.DEFAULT_DURATION);
                }

                values.remove(seekBar);
            }
        }
    }
}
