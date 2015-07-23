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
import com.giganticsheep.wifilight.util.ErrorSubscriber;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by anne on 13/07/15.
 *
 */
class DrawerAdapter extends BaseAdapter {

    @NonNull
    private final Logger logger;

    @Inject EventBus eventBus;
    @Inject BaseLogger baseLogger;
    @Inject Activity activity;

    private boolean allLightsFetched = true;
    private int selectedItem;
    private int position;

    @NonNull
    private final List<ViewData> dataList = new ArrayList<>();

    DrawerAdapter(@NonNull final Injector injector) {
        injector.inject(this);

        logger = new Logger(getClass().getName(), baseLogger);

        eventBus.registerForEvents(this).subscribe(new ErrorSubscriber<DrawerAdapter>(logger));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public String getItem(final int position) {
        return dataList.get(position).getLight().id();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Nullable
    @Override
    public View getView(final int position,
                        @Nullable View convertView,
                        final ViewGroup parent) {
        ViewHolder holder;

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

        return convertView;
    }

    /**
     * Called when all the available {@link com.giganticsheep.wifilight.api.model.Light}s have been fetched from the network.
     *
     * @param event a FetchLightsSuccessEvent
     */
    @Subscribe
    public synchronized void handleFetchLightsSuccess(@NonNull LightControl.FetchLightsSuccessEvent event) {
        logger.debug("handleFetchLightsSuccess()");

        allLightsFetched = true;

        notifyDataSetChanged();

        // TODO this shouldn't be here or it will be called everytime all the lights are fetched
        ((LightControlActivity)activity).drawerListView.performItemClick(null, 0, 0L);
    }

    /**
     * Called every time a {@link com.giganticsheep.wifilight.api.model.Light} has been fetched from the network.
     *
     * @param event contains the fetched {@link com.giganticsheep.wifilight.api.model.Light}.
     */
    @Subscribe
    public synchronized void handleLightDetails(@NonNull LightControl.FetchedLightEvent event) {
        logger.debug("handleLightDetails() " + event.light().id());

        if(allLightsFetched) {
            allLightsFetched = false;

            dataList.clear();
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
        /**
         * Injects the DrawerAdapter class into the Component implementing this interface.
         *
         * @param adapter the class to inject.
         */
        void inject(final DrawerAdapter adapter);
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
                lightNameTextView.setText(viewData.getLight().getLabel());
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

    @Override
    public String toString() {
        return "DrawerAdapter{" +
                "allLightsFetched=" + allLightsFetched +
                ", activity=" + activity +
                ", selectedItem=" + selectedItem +
                ", position=" + position +
                ", dataList=" + dataList +
                '}';
    }
}
