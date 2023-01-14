package com.example.android.router;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class RouteRepository {

    private RouteDao routeDao;
    private LiveData<List<Route>> getRoutes;
    private LiveData<List<Route>> getActiveRoutes;

    // Note that in order to unit test this repo, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    RouteRepository(Application application) {
        RouteRoomDatabase db = RouteRoomDatabase.getDatabase(application);
        routeDao = db.routeDao();
        getRoutes = routeDao.getRoutes();
        getActiveRoutes = routeDao.getActiveRoutes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Route>> getRoutes() {
        return getRoutes;
    }
    LiveData<List<Route>> getActiveRoutes() {
        return getActiveRoutes;
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Route route) {
        RouteRoomDatabase.databaseWriteExecutor.execute(() -> {
            routeDao.insert(route);
        });
    }
}
