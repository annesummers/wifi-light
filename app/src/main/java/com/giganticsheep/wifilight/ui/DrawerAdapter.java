package com.giganticsheep.wifilight.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.giganticsheep.wifilight.R;
import com.giganticsheep.wifilight.api.LightControl;
import com.giganticsheep.wifilight.api.model.Light;
import com.giganticsheep.wifilight.base.BaseLogger;
import com.giganticsheep.wifilight.base.EventBus;
import com.giganticsheep.wifilight.base.Logger;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by anne on 13/07/15.
 * (*_*)
 */
class DrawerAdapter extends BaseAdapter {

    private Logger logger;

    @Inject EventBus eventBus;
    @Inject BaseLogger baseLogger;
    @Inject Activity activity;

    private boolean allLightsFetched = true;
    private int selectedItem;
    private List<ViewData> dataList = null;
    private int position;

    DrawerAdapter(Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);

        eventBus.registerForEvents(this);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Light getItem(int position) {
        return dataList == null ? null : dataList.get(position).getLight();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewData data = null;

        if (dataList.size() - 1 < position) {
            data = new ViewData(position);
            dataList.add(data);
        } else {
            data = dataList.get(position);
        }

        holder.setViewData(data);
        holder.showSelection(selectedItem == position);

        return convertView;
    }

    /**
     * Called when all the available Lights have been fetched from the network.
     *
     * @param event a FetchLightsSuccessEvent
     */
    @Subscribe
    public synchronized void handleFetchLightsSuccess(LightControl.FetchLightsSuccessEvent event) {
        logger.debug("handleFetchLightsSuccess()");

        allLightsFetched = true;
        notifyDataSetChanged();
    }

    /**
     * Called every time a Light has been fetched from the network.
     *
     * @param event a LightDetailsEvent; contains a Light
     */
    @Subscribe
    public synchronized void handleLightDetails(LightControl.LightDetailsEvent event) {
        logger.debug("handleLightDetails() " + event.light().id());

        if(allLightsFetched) {
            allLightsFetched = false;

            dataList = new ArrayList<>();
            position = 0;
        }

        ViewData data = new ViewData(position++);
        data.light = event.light();

        dataList.add(data);
    }

    public interface Injector {
        void inject(DrawerAdapter adapter);
    }

    public class ViewHolder {
        private ViewData viewData;

        private TextView lightNameTextView;
        private ImageView lightStatusImageView;

        public ViewHolder(View view) {
            lightNameTextView = (TextView) view.findViewById(R.id.light_name);
            lightStatusImageView = (ImageView) view.findViewById(R.id.light_status);
        }

        public void setViewData(ViewData viewData) {
            this.viewData = viewData;

            if(viewData.getLight() != null) {
                lightNameTextView.setText(viewData.getLight().getName());
                lightStatusImageView.setImageResource(viewData.getLight().isConnected() ? R.drawable.ic_alert_error : R.drawable.ic_action_reload);
            }
        }

        public void showSelection(boolean show) {

        }
    }

    private class ViewData {
        private int position;

        private Light light;

        public ViewData(int position) {
            this.position = position;
        }

        public Light getLight() {
            return light;
        }
    }
}
