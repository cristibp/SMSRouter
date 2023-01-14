package com.example.android.router;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4.class)
public class RouteDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RouteDao mRouteDao;
    private RouteRoomDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, RouteRoomDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mRouteDao = mDb.RouteDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertAndGetRoute() throws Exception {
        Route Route = new Route("Route");
        mRouteDao.insert(Route);
        List<Route> allRoutes = LiveDataTestUtil.getValue(mRouteDao.getAlphabetizedRoutes());
        assertEquals(allRoutes.get(0).getRoute(), Route.getRoute());
    }

    @Test
    public void getAllRoutes() throws Exception {
        Route Route = new Route("aaa");
        mRouteDao.insert(Route);
        Route Route2 = new Route("bbb");
        mRouteDao.insert(Route2);
        List<Route> allRoutes = LiveDataTestUtil.getValue(mRouteDao.getAlphabetizedRoutes());
        assertEquals(allRoutes.get(0).getRoute(), Route.getRoute());
        assertEquals(allRoutes.get(1).getRoute(), Route2.getRoute());
    }

    @Test
    public void deleteAll() throws Exception {
        Route Route = new Route("Route");
        mRouteDao.insert(Route);
        Route Route2 = new Route("Route2");
        mRouteDao.insert(Route2);
        mRouteDao.deleteAll();
        List<Route> allRoutes = LiveDataTestUtil.getValue(mRouteDao.getAlphabetizedRoutes());
        assertTrue(allRoutes.isEmpty());
    }
}
