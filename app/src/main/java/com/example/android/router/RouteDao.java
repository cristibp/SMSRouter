package com.example.android.router;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
public interface RouteDao {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.

    @Query("SELECT * FROM Route")
    LiveData<List<Route>> getRoutes();

    @Query("SELECT * FROM Route WHERE active = 1")
    List<Route> getActiveRoutes();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Query("DELETE FROM Route")
    void deleteAll();

    @Query("UPDATE Route SET active = 1 WHERE id = :id")
    void activateRoute(Long id);
    @Query("UPDATE Route SET active = 0 WHERE id = :id")
    void deactivateRoute(Long id);
    @Query("DELETE FROM Route WHERE id = :id")
    void delete(Long id);
}
