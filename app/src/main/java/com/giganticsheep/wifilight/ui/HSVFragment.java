package com.giganticsheep.wifilight.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.rx.RXFragment;

import rx.Observable;

/**
 * Created by anne on 22/06/15.
 */
public class HSVFragment extends RXFragment {

    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener();

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

    @Override
    protected void destroyViews() { }

    private SeekBar mHueSeekBar;
    private SeekBar mSaturationSeekBar;
    private SeekBar mValueSeekBar;

    @Override
    protected int layoutId() {
        return R.layout.fragment_hsv;
    }

    @Override
    public Observable<RXFragment> layoutScreen(Configuration configuration) {
        return Observable.empty();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected void initialiseViews(View view) {
        mHueSeekBar = (SeekBar) view.findViewById(R.id.hueSeekBar);
        mSaturationSeekBar = (SeekBar) view.findViewById(R.id.saturationSeekBar);
        mValueSeekBar = (SeekBar) view.findViewById(R.id.valueSeekBar);

        mHueSeekBar.setOnSeekBarChangeListener(mSeekBarChangeListener);
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                if (seekBar.equals(mHueSeekBar)) {
                    ((MainActivity)activity()).setHue(value);
                    return;
                }

                if (seekBar.equals(mSaturationSeekBar)) {
                    ((MainActivity)activity()).setSaturation(value);
                    return;
                }

                if (seekBar.equals(mValueSeekBar)) {
                    ((MainActivity)activity()).setBrightness(value);
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
