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

    private final List<LightNetworkPresenter.GroupViewData> groupDataList = new ArrayList<>();
    private final HashMap<String, List<LightNetworkPresenter.LightViewData>> groupsDataMap = new HashMap<>();

    public void clear() {
        groupDataList.clear();
        groupsDataMap.clear();
    }

    public void add(LightNetworkPresenter.GroupViewData group) {
        groupDataList.add(group);
        groupsDataMap.put(group.getId(), new ArrayList<>());
    }

    public void add(LightNetworkPresenter.LightViewData light) {
        List<LightNetworkPresenter.LightViewData> subList;

        if(groupsDataMap.containsKey(light.getGroupId())) {
            subList = groupsDataMap.get(light.getGroupId());

            subList.add(light);
            groupsDataMap.put(light.getGroupId(), subList);
        }
    }

    public int size() {
        return groupDataList.size();
    }

    public LightNetworkPresenter.LightViewData get(int groupPosition, int childPosition) {
        return groupsDataMap.get(groupDataList.get(groupPosition).getId()).get(childPosition);
    }

    public int lightCount(int groupPosition) {
        return groupsDataMap.get(groupDataList.get(groupPosition).getId()).size();
    }

    public LightNetworkPresenter.GroupViewData get(int groupPosition) {
        return groupDataList.get(groupPosition);
    }

    public boolean lightExists(String groupId, String lightId) {
        List<LightNetworkPresenter.LightViewData> lights = groupsDataMap.get(groupId);
        for(LightNetworkPresenter.LightViewData light : lights) {
            if(light.getId().equals(lightId)) {
                return true;
            }
        }

        return false;
    }

    public void remove(String groupId, String lightId) {
        List<LightNetworkPresenter.LightViewData> lights = groupsDataMap.get(groupId);
        for(LightNetworkPresenter.LightViewData light : lights) {
            if(light.getId().equals(lightId)) {
                lights.remove(light);
                break;
            }
        }
    }
}
