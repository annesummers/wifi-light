package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.util.Constants;

import org.junit.Before;
import org.junit.Test;

import timber.log.Timber;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/07/15. <p>
 * (*_*)
 */
public class LightNetworkTest {

    private LightNetwork lightNetwork;

    @Before
    public void setUp() throws Exception {
        lightNetwork = new LightNetwork();
    }

    @Test
    public void testClear() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        lightNetwork.add(new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID));
        lightNetwork.clear();

        boolean exceptionThrown = false;

        try {
            lightNetwork.get(0, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }

        exceptionThrown = false;

        try {
            lightNetwork.get(0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        } finally {
            assertTrue(exceptionThrown);
        }
    }

    @Test
    public void testAddAndGetLightWithGroup() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertThat(lightNetwork.get(0, 0), equalTo(light));
    }

    @Test
    public void testAddAndGetLightDifferentGroups() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID2, Constants.TEST_GROUP_LABEL2));
        LightViewData light2 = new LightViewData(Constants.TEST_ID2, Constants.TEST_LABEL2, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light2);

        assertThat(lightNetwork.get(0, 0), equalTo(light));
        assertThat(lightNetwork.get(1, 0), equalTo(light2));
    }

    @Test
    public void testAddAndGetLightNoGroup() throws Exception {
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);

        boolean exceptionThrown = false;

        try {
            lightNetwork.add(light);
        } catch (IllegalArgumentException e) {
            Timber.e("testAddAndGetLightNoGroup", e);
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testAddDuplicateLight() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);

        lightNetwork.add(light);

        boolean exceptionThrown = false;

        try {
            lightNetwork.add(light);
        } catch (IllegalArgumentException e) {
            Timber.e("testAddDuplicateLight", e);
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testLightCount() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID2, Constants.TEST_GROUP_LABEL2));
        LightViewData light2 = new LightViewData(Constants.TEST_ID2, Constants.TEST_LABEL2, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light2);

        LightViewData light3 = new LightViewData(Constants.TEST_ID3, Constants.TEST_LABEL3, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light3);

        assertThat(lightNetwork.lightCount(0), equalTo(1));
        assertThat(lightNetwork.lightCount(1), equalTo(2));
    }

    @Test
    public void testAddAndGetGroup() throws Exception {
        GroupViewData group = new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL);
        lightNetwork.add(group);

        assertThat(lightNetwork.get(0), equalTo(group));
    }

    @Test
    public void testAddDuplicateGroup() throws Exception {
        GroupViewData group = new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL);
        lightNetwork.add(group);

        boolean exceptionThrown = false;

        try {
            lightNetwork.add(group);
        } catch (IllegalArgumentException e) {
            Timber.e("testAddDuplicateGroup", e);

            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testGroupCount() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID2, Constants.TEST_GROUP_LABEL2));

        assertThat(lightNetwork.groupCount(), equalTo(2));
    }

    @Test
    public void testLightExistsTrue() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertTrue(lightNetwork.lightExists(Constants.TEST_GROUP_ID, Constants.TEST_ID));
    }

    @Test
    public void testLightExistsFalseWrongGroup() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID2);

        boolean exceptionThrown = false;

        try {
            lightNetwork.add(light);
        } catch(IllegalArgumentException e) {
            Timber.e("testLightExistsFalseWrongGroup", e);

            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testLightExistsFalseWrongLight() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertFalse(lightNetwork.lightExists(Constants.TEST_GROUP_ID, Constants.TEST_ID2));
    }

    @Test
    public void testRemoveExists() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.remove(Constants.TEST_GROUP_ID, Constants.TEST_ID);
    }

    @Test
    public void testRemoveWrongGroup() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        boolean exceptionThrown = false;

        try {
            lightNetwork.remove(Constants.TEST_GROUP_ID2, Constants.TEST_ID);
        } catch(IllegalArgumentException e) {
            Timber.e("testRemoveWrongGroup", e);

            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testRemoveWrongLight() throws Exception {
        lightNetwork.add(new GroupViewData(Constants.TEST_GROUP_ID, Constants.TEST_GROUP_LABEL));
        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.remove(Constants.TEST_GROUP_ID, Constants.TEST_ID2);
    }
}