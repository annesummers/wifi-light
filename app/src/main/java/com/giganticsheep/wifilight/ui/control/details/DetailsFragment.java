package com.giganticsheep.wifilight.ui.control.details;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;

/**
 * Created by anne on 25/06/15.
 * (*_*)
 */

@FragmentArgsInherited
public class DetailsFragment extends Fragment {

    public DetailsFragment() {
        super();
    }

    @InjectView(R.id.nameTextView) TextView nameTextView;
    @InjectView(R.id.id_textview) TextView idTextView;
    @InjectView(R.id.hue_textview) TextView hueTextView;
    @InjectView(R.id.saturation_textview) TextView saturationTextView;
    @InjectView(R.id.brightness_textview) TextView brightnessTextView;
    @InjectView(R.id.kelvin_textview) TextView kelvinTextView;

   /* @Override
    protected int getLayoutRes() {
        return R.layout.fragment_details;
    }

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new DetailsPresenter(getComponent());
    }

    @Override
    protected void initialiseViews(View view) { }

    //@DebugLog
    @Override
    public void showLight(@NonNull final Light light) {
        nameTextView.setText(light.getLabel());
        idTextView.setText(light.getId());
        hueTextView.setText(Integer.toString(light.getHue()));
        saturationTextView.setText(Integer.toString(light.getSaturation()));
        brightnessTextView.setText(Integer.toString(light.getBrightness()));
        kelvinTextView.setText(Integer.toString(light.getKelvin()));
    }

    @Override
    protected void enableViews(boolean enable) { }

    @Override
    protected void destroyViews() { }

    //@DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();

        getPresenter().onDestroy();
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }*/
}
