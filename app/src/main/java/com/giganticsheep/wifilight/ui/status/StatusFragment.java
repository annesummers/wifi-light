package com.giganticsheep.wifilight.ui.status;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.LightSelector;
import com.giganticsheep.wifilight.ui.base.ActivityBase;
import com.giganticsheep.wifilight.ui.base.ComponentBase;
import com.giganticsheep.wifilight.ui.base.FragmentBase;
import com.giganticsheep.wifilight.ui.base.ViewBase;
import com.giganticsheep.wifilight.ui.base.ViewStateBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.hannesdorfmann.fragmentargs.annotation.FragmentArgsInherited;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import timber.log.Timber;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 17/07/15. <p>
 * (*_*)
 */
@FragmentArgsInherited
public abstract class StatusFragment<V extends ViewBase,
                                    P extends StatusPresenterBase<V>,
                                    C extends ComponentBase>
                                                            extends FragmentBase<V, P, C>
                                                            implements ViewBase {

    @InjectView(R.id.power_toggle) protected ToggleButton powerToggle;
    @InjectView(R.id.name_textview) protected TextView nameTextView;
    @InjectView(R.id.all_lights_button) protected Button allLightsButton;

    protected boolean firstSetPower = false;
    protected boolean viewsEnabled = false;

    private LightSelector selector;

    public StatusFragment() {
        super();
    }

    // Views

    @DebugLog
    @OnCheckedChanged(R.id.power_toggle)
    public final synchronized void onPowerToggle(@NonNull final CompoundButton compoundButton,
                                           final boolean isChecked) {
        if(viewsEnabled){
            Timber.d("onPowerToggle() views enabled and firstSetPower is %s", firstSetPower ? "true" : "false");
            if(!firstSetPower) {
                getStatusPresenter().setPower(isChecked, selector);
            }

            firstSetPower = false;
        }
    }

    @OnClick(R.id.all_lights_button)
    public final void onAllLightsClick(@NonNull final View view) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), LightControlActivity.class);
        intent.putExtra(ActivityBase.ANIMATION_EXTRA, ActivityBase.ANIMATION_FADE);

        getActivity().overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
    }

    @Override
    protected final int getLayoutRes() {
        return R.layout.fragment_status;
    }

    @Override
    protected final boolean reinitialiseOnRotate() {
        return false;
    }

    @Override
    protected final boolean animateOnShow() {
        return false;
    }

    @DebugLog
    protected final synchronized void enableViews(final boolean enable) {
        viewsEnabled = enable;
        powerToggle.setEnabled(enable);
    }

    // MVP

    @NonNull
    private StatusPresenterBase getStatusPresenter() {
        return super.getPresenter();
    }

    @NonNull
    @Override
    public ViewStateBase getViewState() {
        return (ViewStateBase) super.getViewState();
    }

    @Override
    public final void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @Override
    public final void showLoading() {
        getViewState().setShowLoading();
    }

    @Override
    public final void showError() {
        getViewState().setShowError();
    }

    @Override
    public final void showError(Throwable throwable) {
        getViewState().setShowError(throwable);
    }
}
