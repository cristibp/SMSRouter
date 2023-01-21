package com.example.android.router;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.UUID;

/**
 * A basic class representing an entity that is a row in a one-column database table.
 *
 * @ Entity - You must annotate the class as an entity and supply a table name if not class name.
 * @ PrimaryKey - You must identify the primary key.
 * @ ColumnInfo - You must supply the column name if it is different from the variable name.
 *
 * See the documentation for the full rich set of annotations.
 * https://developer.android.com/topic/libraries/architecture/room.html
 */

@Entity
public class Route {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "url")
    private final String url;
    @NonNull
    @ColumnInfo(name = "sms")
    private final String sms;

    @ColumnInfo(name = "active")
    private final int active;

    public Route(@NonNull String url, @NonNull String sms, int active) {
        this.url = url;
        this.sms = sms;
        this.active = active;
    }

    @NonNull
    public String getSms() {
        return sms;
    }


    @NonNull
    public String getUrl() {
        return this.url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active == 1;
    }

    public int getActive() {
        return active;
    }
}
