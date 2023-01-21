package com.example.android.router.component;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

import com.example.android.router.Route;
import com.example.android.router.RouteViewModel;

public class MyApplication extends Application {
        private RouteViewModel routeViewModel;

        @Override
        public void onCreate() {
            super.onCreate();
            routeViewModel = new RouteViewModel(this);
        }

    public RouteViewModel getRouteViewModel() {
        return routeViewModel;
    }
}
