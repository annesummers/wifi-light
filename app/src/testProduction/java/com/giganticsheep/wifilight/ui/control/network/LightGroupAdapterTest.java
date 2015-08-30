package com.giganticsheep.wifilight.ui.control.network;

import android.view.View;

import com.giganticsheep.wifilight.ui.UITestBase;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 27/08/15. <p>
 * (*_*)
 */
public class LightGroupAdapterTest extends UITestBase {

    private static final int TEST_LOCATION_POSITION = 0;
    private static final int TEST_GROUP_POSITION = 0;
    private static final int TEST_LIGHT_POSITION = 0;

    private LightGroupAdapter lightGroupAdapter;

    @Before
    public void setUp() {
        lightGroupAdapter = new LightGroupAdapter(component);
        lightGroupAdapter.setLightNetwork(testLightNetwork);
        lightGroupAdapter.setLocationPosition(TEST_LOCATION_POSITION);
    }

    @Test
    public void testGetChildrenCount() throws Exception {
        assertThat(lightGroupAdapter.getChildrenCount(TEST_GROUP_POSITION),
                equalTo(testLightNetwork.lightCount(TEST_LOCATION_POSITION, TEST_GROUP_POSITION)));
    }

    @Test
    public void testGetChild() throws Exception {
        assertThat(lightGroupAdapter.getChild(TEST_GROUP_POSITION, TEST_LIGHT_POSITION),
                equalTo(testLightNetwork.getLight(TEST_LOCATION_POSITION, TEST_GROUP_POSITION, TEST_LIGHT_POSITION)));
    }

    @Test
    public void testGetChildId() throws Exception {
        assertThat(lightGroupAdapter.getChildId(TEST_GROUP_POSITION, TEST_LIGHT_POSITION),
                equalTo(testLightNetwork.getLight(TEST_LOCATION_POSITION, TEST_GROUP_POSITION, TEST_LIGHT_POSITION).getId()));
    }

    @Test
    public void testGetChildView() throws Exception {
        View view = lightGroupAdapter.getGroupView(TEST_GROUP_POSITION, true, null, null);

        assertThat(view.getTag(), not(nullValue()));
    }

    @Test
    public void testGetGroup() throws Exception {
        assertThat(lightGroupAdapter.getGroup(TEST_GROUP_POSITION),
                equalTo(testLightNetwork.getLightGroup(TEST_LOCATION_POSITION, TEST_GROUP_POSITION)));
    }

    @Test
    public void testGetGroupCount() throws Exception {
        assertThat(lightGroupAdapter.getGroupCount(),
                equalTo(testLightNetwork.lightGroupCount(TEST_LOCATION_POSITION)));

    }

    @Test
    public void testGetGroupId() throws Exception {
        assertThat(lightGroupAdapter.getGroupId(TEST_GROUP_POSITION),
                equalTo(testLightNetwork.getLightGroup(TEST_LOCATION_POSITION, TEST_GROUP_POSITION).getId()));
    }

    @Test
    public void testGetGroupView() throws Exception {
        View view = lightGroupAdapter.getChildView(TEST_GROUP_POSITION, TEST_LIGHT_POSITION, true, null, null);

        assertThat(view.getTag(), not(nullValue()));
    }
}