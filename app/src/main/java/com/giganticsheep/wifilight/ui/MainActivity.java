package com.giganticsheep.wifilight.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.WifiLightApplication;
import com.giganticsheep.wifilight.api.network.LightNetwork;
import com.giganticsheep.wifilight.dagger.HasComponent;
import com.giganticsheep.wifilight.ui.base.ActivityLayout;
import com.giganticsheep.wifilight.ui.base.FragmentAttachmentDetails;
import com.giganticsheep.wifilight.ui.base.BaseActivity;
import com.giganticsheep.wifilight.ui.dagger.DaggerMainActivityComponent;
import com.giganticsheep.wifilight.ui.dagger.MainActivityComponent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;


public class MainActivity extends BaseActivity implements HasComponent<MainActivityComponent> {
    public static final float DEFAULT_DURATION = 1.0F;

    private ViewPager viewPager;

    @InjectView(R.id.loading_view) ProgressBar loadingProgressBar;
    @InjectView(R.id.error_view) ImageView errorImageView;
    @InjectView(R.id.light_layout) LinearLayout lightLayout;

    private MainActivityComponent component;

    private String currentLight;
    private List<String> lightIds = new ArrayList<>();
    private ArrayList<String> newLightIds;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // we are attaching the details fragment at position 0 which is under the view pager
            attachNewFragment(new FragmentAttachmentDetails(getString(R.string.fragment_name_light_details), 0, true));
        }

        eventBus.registerForEvents(this);
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean reinitialiseOnRotate() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        eventBus.unregisterForEvents(this);
    }

    public void showErrorView() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        lightLayout.setVisibility(View.INVISIBLE);
        errorImageView.setVisibility(View.VISIBLE);
    }

    public void showLoadingView() {
        errorImageView.setVisibility(View.INVISIBLE);
        lightLayout.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    public void showLightView() {
        errorImageView.setVisibility(View.INVISIBLE);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        lightLayout.setVisibility(View.VISIBLE);
    }

    public String getCurrentLight() {
        return currentLight;
    }

    @Subscribe
    public synchronized void handleFetchLightsSuccess(LightNetwork.FetchLightsSuccessEvent event) {
        lightIds = newLightIds;

        if(lightIds.size() > 0) {
            currentLight = lightIds.get(0);
        }

        newLightIds = null;
    }

    @Subscribe
    public synchronized void handleLightDetails(LightNetwork.LightDetailsEvent event) {
        if(newLightIds == null) {
            newLightIds = new ArrayList<>();
        }

        newLightIds.add(event.light().id());
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
