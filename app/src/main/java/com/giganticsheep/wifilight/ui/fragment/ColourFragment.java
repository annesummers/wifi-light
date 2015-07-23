package com.giganticsheep.wifilight.ui.fragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.mvp.presenter.ColourPresenter;
import com.giganticsheep.wifilight.mvp.presenter.LightPresenterBase;
import com.giganticsheep.wifilight.ui.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;

/**
 * Created by anne on 22/06/15.
 * (*_*)
 */
@FragmentArgsInherited
public class ColourFragment extends LightFragmentBase {

    @InjectView(R.id.hue_seekbar) SeekBar hueSeekBar;
    @InjectView(R.id.saturation_seekbar) SeekBar saturationSeekBar;
    @InjectView(R.id.brightness_seekbar) SeekBar valueSeekBar;

    public ColourFragment() {
        super();

        seekBarChangeListener = new OnLightSeekBarChangeListener(new ColourSeekBarWrapper());
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new ColourPresenter(getLightControlActivity().getComponent(),
                                        getLightControlActivity().getPresenter());
    }

    @NonNull
    public ColourPresenter getLightColourPresenter() {
        return (ColourPresenter) super.getPresenter();
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
    }

    @Override
    protected void showLight(@NonNull final Light light) {
        hueSeekBar.setProgress(light.getHue());
        saturationSeekBar.setProgress(light.getSaturation());
        valueSeekBar.setProgress(light.getBrightness());
    }

    @Override
    protected void enableViews(final boolean enable) {
        //logger.debug("enableViews()");

        hueSeekBar.setEnabled(enable);
        saturationSeekBar.setEnabled(enable);
        valueSeekBar.setEnabled(enable);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    private class ColourSeekBarWrapper implements LightSeekBarWrapper {

        @Override
        public void setLightValue(@NonNull final SeekBar seekBar,
                                  final int value) {
            if (seekBar.equals(hueSeekBar)) {
                getLightColourPresenter().setHue(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(saturationSeekBar)) {
                getLightColourPresenter().setSaturation(value, LightControlActivity.DEFAULT_DURATION);
            } else if (seekBar.equals(valueSeekBar)) {
                getLightColourPresenter().setBrightness(value, LightControlActivity.DEFAULT_DURATION);
            }
        }
    }
}
