package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.dagger.HasComponent;
import com.giganticsheep.wifilight.mvp.presenter.MainActivityPresenter;
import com.giganticsheep.wifilight.mvp.view.LightView;
import com.giganticsheep.wifilight.mvp.view.LightViewState;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.base.BaseActivity;
import com.giganticsheep.wifilight.ui.dagger.DaggerMainActivityComponent;
import com.giganticsheep.wifilight.ui.dagger.MainActivityComponent;
import com.hannesdorfmann.mosby.mvp.viewstate.RestoreableViewState;

import javax.inject.Inject;

import butterknife.InjectView;


public class MainActivity extends BaseActivity<LightView, MainActivityPresenter>
                            implements LightView,
                            HasComponent<MainActivityComponent> {

    public static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;

    @InjectView(R.id.loading_layout) FrameLayout loadingLayout;
    @InjectView(R.id.error_layout) FrameLayout errorLayout;
    @InjectView(R.id.light_layout) LinearLayout lightLayout;

    private MainActivityComponent component;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // we are attaching the details fragment at position 0 which is under the view pager
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 0, true));
        }
    }

    @Override
    protected final ActivityLayout createActivityLayout() {
        return new ActivityLayout() {
            @Override
            public int fragmentContainer(final int position) {
                switch (position) {
                    case 0:
                        return R.id.container;
                    default:
                        return 0;

                }
            }

            @Override
            public int fragmentContainerCount() {
                return 1;
            }

            @Override
            public int layoutId() {
                return R.layout.activity_main;
            }
        };
    }

    @Override
    protected void initialiseViews() {
        super.initialiseViews();

        PagerAdapter pagerAdapter = null;

        if(viewPager != null) {
            pagerAdapter = viewPager.getAdapter();
            pagerAdapter.notifyDataSetChanged();
        }

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter != null ? pagerAdapter : new LightFragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public final boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
                presenter.fetchLights();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return true;
    }

    // Dagger

    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        WifiLightApplication application = ((WifiLightApplication)getApplication());

        component = DaggerMainActivityComponent.builder()
                .wifiLightAppComponent(application.getComponent())
                .build();

        component.inject(this);
    }

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    // MVP

    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(getComponent());
    }

    @Override
    public RestoreableViewState createViewState() {
        return new LightViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        getViewState().apply(this, false);
    }

    @Override
    public void showLoading() {
        getViewState().setShowLoading();

        errorLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLightDetails() {
        getViewState().setShowLightDetails();

        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        showError(new Exception("Unknown error"));
    }

    @Override
    public void showError(Throwable throwable) {
        getViewState().setShowError();

        loadingLayout.setVisibility(View.GONE);
        lightLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.VISIBLE);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_alert_error)
                .setMessage(throwable.getMessage())
                .show();
    }

    @Override
    public void lightChanged(Light light) { }

    public LightViewState getViewState() {
        return (LightViewState) super.getViewState();
    }

    public String getCurrentLight() {
        return presenter.getCurrentLight();
    }

    private class LightFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;

        public LightFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        //this is called when notifyDataSetChanged() is called
        @Override
        public int getItemPosition(Object object) {
            // refresh all fragments when data set changed i.e. recreate them all
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            try {
                switch(position) {
                    case 0:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_colour));
                    case 1:
                        return fragmentFactory.createFragment(getString(R.string.fragment_name_light_effects));
                    default:
                        return null;
                }
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return getString(R.string.fragment_name_light_colour);
                case 1:
                    return getString(R.string.fragment_name_light_effects);
                default:
                    return null;
            }
        }
    }
}
