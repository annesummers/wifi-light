package com.giganticsheep.wifilight.ui.control;

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

import javax.inject.Inject;

import hugo.weaving.DebugLog;

/**
 * Created by anne on 13/07/15.
 *
 */
public class DrawerAdapter extends BaseAdapter {

    @Inject Activity activity;

    private int position;
    private LightNetwork lightNetwork = new LightNetwork();

    @DebugLog
    public DrawerAdapter(@NonNull final Injector injector) {
        injector.inject(this);
    }

    @Override
    public int getCount() {
        return lightNetwork.size();
    }

    @Nullable
    @Override
    public String getItem(final int position) {
        return lightNetwork.get(position).getId();
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @DebugLog
    @Nullable
    @Override
    public View getView(final int position,
                        @Nullable View convertView,
                        final ViewGroup parent) {
        LightViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.drawer_list_item, null);
            holder = new LightViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LightViewHolder) convertView.getTag();
        }

        holder.setViewData(lightNetwork.get(position));

        return convertView;
    }

    public void setLightNetwork(@NonNull final LightNetwork lightNetwork, final int position) {
        this.lightNetwork = lightNetwork;
        this.position = position;
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
    public class LightViewHolder {
        private LightNetworkPresenter.LightViewData viewData;

        private final TextView lightNameTextView;
        private final ImageView lightStatusImageView;

        public LightViewHolder(@NonNull View view) {
            lightNameTextView = (TextView) view.findViewById(R.id.light_name);
            lightStatusImageView = (ImageView) view.findViewById(R.id.light_status);
        }

        public void setViewData(@NonNull final LightNetworkPresenter.LightViewData viewData) {
            this.viewData = viewData;

            lightNameTextView.setText(viewData.getLabel());
            lightStatusImageView.setImageResource(viewData.isConnected() ?
                    R.drawable.ic_action_tick :
                    R.drawable.ic_action_warning);
        }
    }
}
