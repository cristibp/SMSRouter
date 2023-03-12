package com.example.android.router;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.android.router.component.MyApplication;


public class RouteListAdapter extends ListAdapter<Route, RouteViewHolder> {

    private final RouteViewModel mRouteViewModel;
    private final MyApplication applicationContext;

    public RouteListAdapter(@NonNull DiffUtil.ItemCallback<Route> diffCallback, RouteViewModel mRouteViewModel, MyApplication applicationContext) {
        super(diffCallback);
        this.mRouteViewModel = mRouteViewModel;
        this.applicationContext = applicationContext;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RouteViewHolder.create(parent, mRouteViewModel);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        Route current = getItem(position);
        holder.bind(current.getUrl(), current.getSms(), current.isActive());
        holder.getRemoveButton().setOnClickListener(v -> mRouteViewModel.remove(current.getId()));
        holder.getStatus().setOnClickListener(v -> {
            mRouteViewModel.flipRouteActivation(current);
        });
        holder.getTextViewURL().setOnLongClickListener(v -> {
            String textViewUrl = holder.getTextViewURL().getText().toString();
            ClipboardManager clipboard = (ClipboardManager) applicationContext.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("url", textViewUrl);
            clipboard.setPrimaryClip(clip);
            return true;
        });
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
