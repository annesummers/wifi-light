package com.giganticsheep.wifilight.ui.colour;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightSeekBarWrapper;
import com.giganticsheep.wifilight.ui.base.light.OnLightSeekBarChangeListener;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import hugo.weaving.DebugLog;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class ColourFragment extends LightFragmentBase {

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar brightnessSeekBar;

    public ColourFragment() {
        super();

        seekBarChangeListener = new OnLightSeekBarChangeListener(new ColourSeekBarWrapper());
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new ColourPresenter(getComponent());
    }

    @NonNull
    private ColourPresenter getLightColourPresenter() {
        return (ColourPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_colour;
    }

    //@DebugLog
    @Override
    protected void initialiseViews(View view) {
        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        saturationSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        brightnessSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    //@DebugLog
    @Override
    public void showLight(@NonNull final Light light) {
        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        brightnessSeekBar.setProgress(light.getBrightness());
    }

    @Override
    protected void enableViews(final boolean enable) {
        hueSeekBar.setEnabled(enable);
        saturationSeekBar.setEnabled(enable);
        brightnessSeekBar.setEnabled(enable);
    }

    private class ColourSeekBarWrapper implements LightSeekBarWrapper {

        @Override
        public void setLightValue(@NonNull final SeekBar seekBar,
                                  final int value) {
            if (seekBar.equals(hueSeekBar)) {
                getLightColourPresenter().setHue(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(saturationSeekBar)) {
                getLightColourPresenter().setSaturation(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(brightnessSeekBar)) {
                getLightColourPresenter().setBrightness(value, LightControlActivity.DEFAULT_DURATION);
            }
        }
    }
}
