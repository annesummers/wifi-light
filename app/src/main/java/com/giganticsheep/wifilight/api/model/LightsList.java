package com.giganticsheep.wifilight.api.model;


import java.util.ArrayList;

/**
 * Created by anne on 23/06/15.
 * (*_*)
 */
public class LightsList extends ArrayList<Light> {

  /*  Light findLight(long id) {
        Iterator<Light> iterator = iterator();
        while (iterator.hasNext()) {
            Light light = iterator.next();
            if(light.id() == id) {
                return light;
            }
        }

        return null;
    }

    LightsList lightSubList(String label) {
        LightsList subList = new LightsList();
        Iterator<Light> iterator = iterator();

        while (iterator.hasNext()) {
            Light light = iterator.next();
            if(light.labels().contains(label)) {
                subList.add(light);
            }
        }

        return subList;
    }*/

   /* LightsList locationSubList(long groupId) {

    }

    LightsList locationSubList(String label) {

    }*/
}
