package com.example.android.router;


import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


public class RouteListAdapter extends ListAdapter<Route, RouteViewHolder> {

    public RouteListAdapter(@NonNull DiffUtil.ItemCallback<Route> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RouteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        Route current = getItem(position);
        holder.bind(current.getUrl(), current.getSms(), current.isActive());
    }

    static class RouteDiff extends DiffUtil.ItemCallback<Route> {

        @Override
        public boolean areItemsTheSame(@NonNull Route oldItem, @NonNull Route newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Route oldItem, @NonNull Route newItem) {
            return oldItem.getUrl().equals(newItem.getUrl()) && oldItem.getSms().equals(newItem.getSms());
        }
    }
}
