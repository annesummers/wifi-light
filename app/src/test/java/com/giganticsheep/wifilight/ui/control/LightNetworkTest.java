package com.giganticsheep.wifilight.ui.control;

import com.giganticsheep.wifilight.api.model.Group;
import com.giganticsheep.wifilight.api.model.Location;
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
    public void setUp() {
        lightNetwork = new LightNetwork();
    }

    @Test
    public void testAddLocation() {
        GroupData location = new GroupData();
        location.name = Constants.TEST_LOCATION_LABEL;
        location.id = Constants.TEST_LOCATION_ID;
        lightNetwork.add((Location) location);

        assertThat(lightNetwork.getLocation().getId(), equalTo(Constants.TEST_LOCATION_ID));
    }

    @Test
    public void testClear() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

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
    public void testAddAndGetLightWithGroup() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertThat(lightNetwork.get(0, 0), equalTo(light));
    }

    @Test
    public void testAddAndGetLightDifferentGroups() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        GroupData group2 = new GroupData();
        group2.name = Constants.TEST_GROUP_LABEL2;
        group2.id = Constants.TEST_GROUP_ID2;
        lightNetwork.add((Group) group2);

        LightViewData light2 = new LightViewData(Constants.TEST_ID2, Constants.TEST_LABEL2, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light2);

        assertThat(lightNetwork.get(0, 0), equalTo(light));
        assertThat(lightNetwork.get(1, 0), equalTo(light2));
    }

    @Test
    public void testAddAndGetLightNoGroup() {
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
    public void testAddDuplicateLight() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

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
    public void testLightCount() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        GroupData group2 = new GroupData();
        group2.name = Constants.TEST_GROUP_LABEL2;
        group2.id = Constants.TEST_GROUP_ID2;
        lightNetwork.add((Group) group2);

        LightViewData light2 = new LightViewData(Constants.TEST_ID2, Constants.TEST_LABEL2, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light2);

        LightViewData light3 = new LightViewData(Constants.TEST_ID3, Constants.TEST_LABEL3, true, Constants.TEST_GROUP_ID2);
        lightNetwork.add(light3);

        assertThat(lightNetwork.lightCount(0), equalTo(1));
        assertThat(lightNetwork.lightCount(1), equalTo(2));
    }

    @Test
    public void testAddAndGetGroup() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        assertThat(lightNetwork.get(0), equalTo(group));
    }

    @Test
    public void testAddDuplicateGroup() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        boolean exceptionThrown = false;

        try {
            lightNetwork.add((Group) group);
        } catch (IllegalArgumentException e) {
            Timber.e("testAddDuplicateGroup", e);

            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
    }

    @Test
    public void testGroupCount() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        GroupData group2 = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL2;
        group.id = Constants.TEST_GROUP_ID2;
        lightNetwork.add((Group) group2);

        assertThat(lightNetwork.groupCount(), equalTo(2));
    }

    @Test
    public void testLightExistsTrue() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertTrue(lightNetwork.lightExists(Constants.TEST_GROUP_ID, Constants.TEST_ID));
    }

    @Test
    public void testLightExistsFalseWrongGroup() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

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
    public void testLightExistsFalseWrongLight() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        assertFalse(lightNetwork.lightExists(Constants.TEST_GROUP_ID, Constants.TEST_ID2));
    }

    @Test
    public void testRemoveExists() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.remove(Constants.TEST_GROUP_ID, Constants.TEST_ID);
    }

    @Test
    public void testRemoveWrongGroup() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

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
    public void testRemoveWrongLight() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        LightViewData light = new LightViewData(Constants.TEST_ID, Constants.TEST_LABEL, true, Constants.TEST_GROUP_ID);
        lightNetwork.add(light);

        lightNetwork.remove(Constants.TEST_GROUP_ID, Constants.TEST_ID2);
    }

    @Test
    public void testGroupExistsTrue() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        assertTrue(lightNetwork.groupExists(Constants.TEST_GROUP_ID));
    }

    @Test
    public void testGroupExistsFalse() {
        GroupData group = new GroupData();
        group.name = Constants.TEST_GROUP_LABEL;
        group.id = Constants.TEST_GROUP_ID;
        lightNetwork.add((Group) group);

        assertFalse(lightNetwork.groupExists(Constants.TEST_GROUP_ID2));

    }
}