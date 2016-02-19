package com.giganticsheep.wifilight.ui.control;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.giganticsheep.nofragmentbase.ui.base.RelativeLayoutContainer;
import com.giganticsheep.nofragmentbase.ui.base.Screen;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.model.Light;

import butterknife.InjectView;

/**
 * Created by anne on 19/02/16.
 */
public class LightControlContainer extends RelativeLayoutContainer<LightScreen>
                                    implements LightScreen.ViewAction, LightViewState {
    private Light light;

    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    @InjectView(R.id.pager)
    ViewPager viewPager;

    public LightControlContainer(Context context) {
        super(context);
    }

    public LightControlContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Screen.ViewActionBase getViewAction() {
        return this;
    }

    @Override
    protected void setupViews() {
        PagerAdapter pagerAdapter = null;

        if(viewPager != null) {
            pagerAdapter = viewPager.getAdapter();
            pagerAdapter.notifyDataSetChanged();
        }

        viewPager.setAdapter(pagerAdapter != null ? pagerAdapter : new LightPagerAdapter());

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreated() { }

    @Override
    public void showLight(Light light) {
        if(light != this.light) {
            this.light = light;
        }
    }

    @Override
    protected void showData() {

    }

    @Override
    public void setStateError(String error) {
        hideConnecting();
        hideDisconnected();

        super.setStateError(error);
    }

    @Override
    public void setStateError(Throwable throwable) {
        hideConnecting();
        hideDisconnected();

        super.setStateError(throwable);
    }

    @Override
    public void setStateLoading() {
        hideConnecting();
        hideDisconnected();

        super.setStateLoading();
    }

    @Override
    public void setStateShowing() {
        hideConnecting();
        hideDisconnected();

        super.setStateShowing();
    }

    @Override
    public void setStateDisconnected() {
        hideConnecting();
        hideLoading();

        showDisconnected();
    }

    @Override
    public void setStateConnecting() {
        hideDisconnected();
        hideLoading();

        showConnecting();
    }

    protected void showConnecting() {
        getScreenGroup().postControlEvent(new LightControlActivity.ConnectingEvent(true));
    }

    protected void hideConnecting() {
        getScreenGroup().postControlEvent(new LightControlActivity.ConnectingEvent(false));
    }

    protected void showDisconnected() {
        getScreenGroup().postControlEvent(new LightControlActivity.DisconnectedEvent(true));
    }

    protected void hideDisconnected() {
        getScreenGroup().postControlEvent(new LightControlActivity.DisconnectedEvent(false));
    }

    private class LightPagerAdapter extends PagerAdapter {

        public LightPagerAdapter() { }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
