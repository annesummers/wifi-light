package com.giganticsheep.wifilight.api.model;

import com.giganticsheep.wifilight.api.network.LocationData;
import com.giganticsheep.wifilight.util.Constants;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 28/07/15. <p>
 * (*_*)
 */
public class LightNetworkTest extends ModelTest {

    @Test
    public void testAddLocation() {
        LightNetwork lightNetwork = new LightNetwork();

        LocationData location = new LocationData();
        location.name = Constants.TEST_LOCATION_LABEL;
        location.id = Constants.TEST_LOCATION_ID;
        lightNetwork.addLightLocation(location);

        int locationCount = lightNetwork.lightLocationCount();

        assertThat(lightNetwork.getLightLocation(locationCount - 1).getId(),
                equalTo(Constants.TEST_LOCATION_ID));
    }

    @Test
    public void testLocationCount() {
        assertThat(testLightNetwork.lightLocationCount(), equalTo(1));
    }

    @Test
    public void testGetLocationLocationInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightLocation(Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLocationLocationOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightLocation(2);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGroupCount() {
        assertThat(testLightNetwork.lightGroupCount(0), equalTo(2));
    }

    @Test
    public void testGetGroup() {
        assertThat(testLightNetwork.getLightGroup(0, 0).getId(), equalTo(Constants.TEST_GROUP_ID));
    }

    @Test
    public void testGroupCountLocationInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightGroupCount(Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetGroupLocationInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightGroup(Constants.INVALID, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGroupCountGroupInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightGroup(0, Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetGroupGroupInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightGroup(0, Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGroupCountLocationOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightGroupCount(2);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetGroupLocationOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightGroup(2, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetGroupGroupOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLightGroup(0, 2);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testLightCount() {
        assertThat(testLightNetwork.lightCount(0, 0), equalTo(2));
    }

    @Test
    public void testLightCountLocationInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightCount(Constants.INVALID, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testLightCountGroupInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightCount(0, Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testLightCountLocationOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightCount(2, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testLightCountGroupOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.lightCount(0, 2);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLight() {
        assertThat(testLightNetwork.getLight(0, 0, 0).getId(), equalTo(Constants.TEST_ID));
    }

    @Test
    public void testGetLightLocationInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(Constants.INVALID, 0, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLightGroupInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(0, Constants.INVALID, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLightLightInvalid() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(0, 0, Constants.INVALID);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLightLocationOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(2, 0, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLightGroupOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(0, 2, 0);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }

    @Test
    public void testGetLightLightOutOfBounds() {
        boolean exceptionThrown = false;

        try {
            testLightNetwork.getLight(0, 0, 2);
        } catch(IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }

        assertThat(exceptionThrown, equalTo(true));
    }
}