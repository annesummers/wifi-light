package com.giganticsheep.wifilight.ui.control.network;

import android.view.View;
import android.widget.ExpandableListView;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.ui.UITestBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.locations.LightLocationAdapter;
import com.giganticsheep.wifilight.ui.locations.LightNetworkDrawerFragment;
import com.giganticsheep.wifilight.ui.locations.LightNetworkDrawerFragmentComponent;
import com.giganticsheep.wifilight.ui.locations.LightNetworkDrawerFragmentModule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.SupportFragmentTestUtil;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 27/08/15. <p>
 * (*_*)
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LightLocationAdapterTest extends UITestBase {

    private static final int TEST_LOCATION_POSITION = 0;
    private static final long TEST_GROUP_POSITION = 0L;
    private static final long TEST_LIGHT_POSITION = 0L;

    private LightLocationAdapter lightLocationAdapter;

    private LightNetworkDrawerFragment fragment;

    private LightNetworkDrawerFragmentComponent drawerFragmentComponent;

    @Override
    protected void createComponentAndInjectDependencies() {
        super.createComponentAndInjectDependencies();

        try {
            fragment = (LightNetworkDrawerFragment) fragmentFactory.createFragment("Drawer");
        } catch(Exception e) {
            Assert.fail("Drawer fragment does not exist");
        }

        SupportFragmentTestUtil.startFragment(fragment, LightControlActivity.class);

        drawerFragmentComponent = DaggerLightNetworkDrawerFragmentComponent.builder()
                .wifiLightAppComponent(appComponent)
                .lightNetworkDrawerFragmentModule(new LightNetworkDrawerFragmentModule(fragment))
                .build();
    }

    @Before
    public void setUp() {
        lightLocationAdapter = new LightLocationAdapter(drawerFragmentComponent,
                new LightNetworkClickListener(drawerFragmentComponent, (ExpandableListView) fragment.getView().findViewById(R.id.location_list)));
        lightLocationAdapter.setLightNetwork(testLightNetwork);
    }

    @Test
    public void testGetChildrenCount() throws Exception {
        assertThat(lightLocationAdapter.getChildrenCount((int) TEST_LOCATION_POSITION),
                equalTo(testLightNetwork.lightCount(TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION)));
    }

    @Test
    public void testGetChild() throws Exception {
        assertThat(lightLocationAdapter.getChild((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION),
                equalTo(testLightNetwork.getLightGroup((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION).getId()));
    }

    @Test
    public void testGetChildId() throws Exception {
        assertThat(lightLocationAdapter.getChildId((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION),
                equalTo(TEST_LIGHT_POSITION));
    }

    @Test
    public void testGetChildView() throws Exception {
        View view = lightLocationAdapter.getChildView((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION, true, null, null);

        Object tag = view.getTag();
        assertThat(tag, not(nullValue()));
        assertThat(tag, instanceOf(LightLocationAdapter.GroupListViewHolder.class));

        LightLocationAdapter.GroupListViewHolder viewHolder = (LightLocationAdapter.GroupListViewHolder)tag;
    }

    @Test
    public void testGetGroup() throws Exception {
        assertThat(lightLocationAdapter.getGroup((int) TEST_LOCATION_POSITION),
                equalTo(testLightNetwork.getLightLocation(TEST_LOCATION_POSITION).getId()));
    }

    @Test
    public void testGetGroupCount() throws Exception {
        assertThat(lightLocationAdapter.getGroupCount(),
                equalTo(testLightNetwork.lightGroupCount(TEST_LOCATION_POSITION)));

    }

    @Test
    public void testGetGroupId() throws Exception {
        assertThat(lightLocationAdapter.getGroupId((int) TEST_LOCATION_POSITION),
                equalTo(TEST_GROUP_POSITION));
    }

    @Test
    public void testGetGroupView() throws Exception {
        View view = lightLocationAdapter.getGroupView((int) TEST_LOCATION_POSITION, true, null, null);

        Object tag = view.getTag();
        assertThat(tag, not(nullValue()));
        assertThat(tag, instanceOf(LightLocationAdapter.LocationViewHolder.class));

        LightLocationAdapter.LocationViewHolder viewHolder = (LightLocationAdapter.LocationViewHolder)tag;

        assertThat(viewHolder.locationNameTextView.getText(),
                equalTo(testLightNetwork.getLightLocation(TEST_LOCATION_POSITION).getName()));
    }
}