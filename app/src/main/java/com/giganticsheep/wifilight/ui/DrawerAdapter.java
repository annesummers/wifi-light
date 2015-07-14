package com.giganticsheep.wifilight.ui;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    private final Logger logger;

    @Inject EventBus eventBus;
    @Inject BaseLogger baseLogger;
    @Inject Activity activity;

    private boolean allLightsFetched = true;
    private int selectedItem;
    @Nullable
    private List<ViewData> dataList = null;
    private int position;

    DrawerAdapter(@NonNull Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);

        eventBus.registerForEvents(this);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return dataList == null ? null : dataList.get(position).getLight().id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ViewData data = null;

        if(dataList != null) {
            if (dataList.size() - 1 < position) {
                data = new ViewData(position);
                dataList.add(data);
            } else {
                data = dataList.get(position);
            }
        }

        holder.setViewData(data);

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

        ((LightControlActivity)activity).drawerListView.performItemClick(null, 0, 0L);
    }

    /**
     * Called every time a Light has been fetched from the network.
     *
     * @param event a LightDetailsEvent; contains a Light
     */
    @Subscribe
    public synchronized void handleLightDetails(@NonNull LightControl.LightDetailsEvent event) {
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

    /**
     * The Injector interface is implemented by a Component that provides the injected
     * class members, enabling the DrawerAdapter to inject itself
     * into the Component.
     */
    public interface Injector {
        void inject(DrawerAdapter adapter);
    }

    /**
     * Holds the views for an entry in the list.  Once an instance of this class has been created
     * for an entry and the views inflated it is stored in the tag for the View for that list position.
     * This enables the views to be recycled quickly when the elements change or the list is scrolled
     * off the screen. <p>
     *
     * The data for the entry is set via setViewData(ViewData viewData).
     */
    public class ViewHolder {
        private ViewData viewData;

        private final TextView lightNameTextView;
        private final ImageView lightStatusImageView;

        public ViewHolder(@NonNull View view) {
            lightNameTextView = (TextView) view.findViewById(R.id.light_name);
            lightStatusImageView = (ImageView) view.findViewById(R.id.light_status);
        }

        public void setViewData(@NonNull ViewData viewData) {
            this.viewData = viewData;

            if(viewData.getLight() != null) {
                lightNameTextView.setText(viewData.getLight().getName());
                lightStatusImageView.setImageResource(viewData.getLight().isConnected() ?
                        R.drawable.ic_action_tick :
                        R.drawable.ic_action_warning);
            }
        }
    }

    private class ViewData {
        private final int position;

        private Light light;

        public ViewData(int position) {
            this.position = position;
        }

        public Light getLight() {
            return light;
        }
    }
}
