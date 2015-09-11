package com.giganticsheep.wifilight.ui.control.network;

import android.view.View;

import com.giganticsheep.wifilight.BuildConfig;
import com.giganticsheep.wifilight.ui.UITestBase;
import com.giganticsheep.wifilight.ui.control.LightControlActivity;
import com.giganticsheep.wifilight.ui.locations.LightGroupAdapter;
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
public class LightGroupAdapterTest extends UITestBase {

    private static final int TEST_LOCATION_POSITION = 0;
    private static final long TEST_GROUP_POSITION = 0L;
    private static final long TEST_LIGHT_POSITION = 0L;

    private LightGroupAdapter lightGroupAdapter;

    private LightNetworkDrawerFragmentComponent drawerFragmentComponent;

    @Override
    protected void createComponentAndInjectDependencies() {
        super.createComponentAndInjectDependencies();

        LightNetworkDrawerFragment fragment = null;

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
        lightGroupAdapter = new LightGroupAdapter(drawerFragmentComponent);
        lightGroupAdapter.setLightNetwork(testLightNetwork);
        lightGroupAdapter.setLocationPosition(TEST_LOCATION_POSITION);
    }

    @Test
    public void testGetChildrenCount() throws Exception {
        assertThat(lightGroupAdapter.getChildrenCount((int) TEST_GROUP_POSITION),
                equalTo(testLightNetwork.lightCount(TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION)));
    }

    @Test
    public void testGetChild() throws Exception {
        assertThat(lightGroupAdapter.getChild((int) TEST_GROUP_POSITION, (int) TEST_LIGHT_POSITION),
                equalTo(testLightNetwork.getLight((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION, (int) TEST_LIGHT_POSITION).getId()));
    }

    @Test
    public void testGetChildId() throws Exception {
        assertThat(lightGroupAdapter.getChildId((int) TEST_GROUP_POSITION, (int) TEST_LIGHT_POSITION),
                equalTo(TEST_LIGHT_POSITION));
    }

    @Test
    public void testGetChildView() throws Exception {
        View view = lightGroupAdapter.getChildView((int) TEST_GROUP_POSITION, (int) TEST_LIGHT_POSITION, true, null, null);

        Object tag = view.getTag();
        assertThat(tag, not(nullValue()));
        assertThat(tag, instanceOf(LightGroupAdapter.LightViewHolder.class));

        LightGroupAdapter.LightViewHolder viewHolder = (LightGroupAdapter.LightViewHolder)tag;

        assertThat(viewHolder.lightNameTextView.getText(),
                equalTo(testLightNetwork.getLight((int) TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION, (int) TEST_LIGHT_POSITION).getLabel()));

    }

    @Test
    public void testGetGroup() throws Exception {
        assertThat(lightGroupAdapter.getGroup((int) TEST_GROUP_POSITION),
                equalTo(testLightNetwork.getLightGroup(TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION).getId()));
    }

    @Test
    public void testGetGroupCount() throws Exception {
        assertThat(lightGroupAdapter.getGroupCount(),
                equalTo(testLightNetwork.lightGroupCount(TEST_LOCATION_POSITION)));

    }

    @Test
    public void testGetGroupId() throws Exception {
        assertThat(lightGroupAdapter.getGroupId((int) TEST_GROUP_POSITION),
                equalTo(TEST_GROUP_POSITION));
    }

    @Test
    public void testGetGroupView() throws Exception {
        View view = lightGroupAdapter.getGroupView((int) TEST_GROUP_POSITION, true, null, null);

        Object tag = view.getTag();
        assertThat(tag, not(nullValue()));
        assertThat(tag, instanceOf(LightGroupAdapter.GroupViewHolder.class));

        LightGroupAdapter.GroupViewHolder viewHolder = (LightGroupAdapter.GroupViewHolder)tag;

        assertThat(viewHolder.groupNameTextView.getText(),
                equalTo(testLightNetwork.getLightGroup(TEST_LOCATION_POSITION, (int) TEST_GROUP_POSITION).getName()));
    }
}