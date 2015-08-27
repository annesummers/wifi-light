package com.giganticsheep.wifilight.ui.status;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.attention.ShakeAnimator;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.ui.base.light.LightPresenterBase;
import com.giganticsheep.wifilight.ui.base.light.LightFragmentBase;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public class StatusFragment extends LightFragmentBase {

    @InjectView(R.id.power_toggle) ToggleButton powerToggle;
    @InjectView(R.id.status_textview) TextView statusTextView;

    private boolean firstSetPower = false;
    private boolean viewsEnabled = false;

    public StatusFragment() {
        super();
    }

    // Views

    @DebugLog
    @OnCheckedChanged(R.id.power_toggle)
    public synchronized void onPowerToggle(@NonNull final CompoundButton compoundButton,
                                           final boolean isChecked) {
        if(viewsEnabled){
            Timber.d("onPowerToggle() views enabled and firstSetPower is %s", firstSetPower ? "true" : "false");
            if(!firstSetPower) {
                getLightStatusPresenter().setPower(isChecked);
            }

            firstSetPower = false;
        }
    }

    // MVP

    @NonNull
    @Override
    public LightPresenterBase createPresenter() {
        return new StatusPresenter(getLightControlActivity().getComponent());
    }

    @NonNull
    private StatusPresenter getLightStatusPresenter() {
        return (StatusPresenter) super.getPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_status;
    }

    @DebugLog
    @Override
    public synchronized void showLight(@NonNull final Light light) {
        firstSetPower = true;
        powerToggle.setChecked(light.getPower() == LightControl.Power.ON);

        String oldStatus = (String) statusTextView.getText();
        statusTextView.setText(getString(light.isConnected() ? R.string.label_light_connected
                                                             : R.string.label_light_disconnected));

        if(!oldStatus.equals(statusTextView.getText())) {
            YoYo.with(Techniques.Shake)
                    .duration(ShakeAnimator.DURATION)
                    .playOn(statusTextView);
        }
    }

    @DebugLog
    @Override
    protected synchronized void enableViews(final boolean enable) {
        viewsEnabled = false;
        powerToggle.setEnabled(enable);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return false;
    }
}
