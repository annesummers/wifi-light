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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener();

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

    private SeekBar hueSeekBar;
    private SeekBar saturationSeekBar;
    private SeekBar valueSeekBar;

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
        hueSeekBar = (SeekBar) view.findViewById(R.id.hueSeekBar);
        saturationSeekBar = (SeekBar) view.findViewById(R.id.saturationSeekBar);
        valueSeekBar = (SeekBar) view.findViewById(R.id.valueSeekBar);

        hueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private class OnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
            if(fromUser) {
                if (seekBar.equals(hueSeekBar)) {
                    ((MainActivity)activity()).setHue(value);
                    return;
                }

                if (seekBar.equals(saturationSeekBar)) {
                    ((MainActivity)activity()).setSaturation(value);
                    return;
                }

                if (seekBar.equals(valueSeekBar)) {
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
