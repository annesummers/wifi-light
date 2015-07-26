package com.giganticsheep.wifilight.ui.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DESCRIPTION HERE ANNE <p>
 * Created by anne on 24/07/15. <p>
 * (*_*)
 */
public class LightNetwork {

    private final List<LightNetworkPresenter.LightViewData> lightDataList = new ArrayList<>();
    private final HashMap<LightNetworkPresenter.GroupViewData, List<LightNetworkPresenter.LightViewData>> groupDataMap = new HashMap<>();

    public void clear() {
        lightDataList.clear();
        groupDataMap.clear();
    }

    public void add(LightNetworkPresenter.GroupViewData group) {
        List<LightNetworkPresenter.LightViewData> subList = new ArrayList<>();

        for(LightNetworkPresenter.LightViewData light : lightDataList) {
            if(light.getGroupId().equals(group.getId())) {
                subList.add(light);
            }
        }

        groupDataMap.put(group, subList);
    }

    public void add(LightNetworkPresenter.LightViewData light) {
        lightDataList.add(light);

        List<LightNetworkPresenter.LightViewData> subList;

        for(LightNetworkPresenter.GroupViewData group : groupDataMap.keySet()) {
            if (group.getId().equals(light.getGroupId())) {
                subList = groupDataMap.get(group);
                subList.add(light);

                groupDataMap.put(group, subList);

                break;
            }
        }
    }

    public int size() {
        return lightDataList.size();
    }

    public LightNetworkPresenter.LightViewData get(final int position) {
        return lightDataList.get(position);
    }
}
