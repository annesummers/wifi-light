package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.model.LightConstants;
import com.giganticsheep.wifilight.mvp.presenter.LightColourPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class LightColourFragment extends LightFragmentBase {

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;
    @InjectView(R.id.kelvin_seekbar) SeekBar kelvinSeekBar;

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
    protected void showLight(Light light) {
        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        valueSeekBar.setProgress(light.getBrightness());
        kelvinSeekBar.setProgress(light.getKelvin() - LightConstants.KELVIN_BASE);
    }

    @Override
    protected void enableViews(boolean enable) {
        //logger.debug("enableViews()");

        hueSeekBar.setEnabled(enable);
        saturationSeekBar.setEnabled(enable);
        valueSeekBar.setEnabled(enable);
        kelvinSeekBar.setEnabled(enable);
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
