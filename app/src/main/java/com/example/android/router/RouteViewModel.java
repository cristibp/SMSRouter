package com.example.android.router;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

public class RouteViewModel extends AndroidViewModel {

    private final RouteRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Route>> allRoutes;
    private final List<Route> activeRoutes;

    public RouteViewModel(Application application) {
        super(application);
        mRepository = new RouteRepository(application);
        allRoutes = mRepository.getRoutes();
        activeRoutes = mRepository.getActiveRoutes();
    }

    public LiveData<List<Route>> getAllRoutes() {
        return allRoutes;
    }

    public List<Route> getActiveRoutes() {
        return activeRoutes;
    }

    public Map<String, String> getRoutesBySMS() {
        Map<String, String> map = new HashMap<>();
        List<Route> activeRoutesList = getActiveRoutes();
        if (activeRoutesList == null) {
            return Collections.emptyMap();
        }
        for (Route value : activeRoutesList) {
            map.put(value.getSms(), value.getUrl());
        }
        return map;
    }

    public void insert(Route route) {
        mRepository.insert(route);
    }

    public void remove(Long id) {
        mRepository.delete(id);
    }

    public void flipRouteActivation(Route route) {
        if (route.isActive()) {
            mRepository.deactivateRoute(route.getId());
        } else {
            mRepository.activateRoute(route.getId());
        }
    }
}

