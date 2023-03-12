package com.example.android.router;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.router.component.MyApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.android.support.DaggerAppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_ROUTE_ACTIVITY_REQUEST_CODE = 1;

   private RouteViewModel mRouteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        // Get a new or existing ViewModel from the ViewModelProvider.
        MyApplication applicationContext = (MyApplication) super.getApplicationContext();
        mRouteViewModel = applicationContext.getRouteViewModel();

       // mRouteViewModel = new ViewModelProvider(this).get(RouteViewModel.class);

        final RouteListAdapter adapter = new RouteListAdapter(new RouteListAdapter.RouteDiff(), mRouteViewModel,applicationContext);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 10);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        // Update the cached copy of the routes in the adapter.
        mRouteViewModel.getAllRoutes().observe(this, adapter::submitList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewRouteActivity.class);
            startActivityForResult(intent, NEW_ROUTE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ROUTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Route route = new Route(data.getStringExtra(NewRouteActivity.EXTRA_REPLY_URL), data.getStringExtra(NewRouteActivity.EXTRA_REPLY_PHONE_NUMBER), 1);
            mRouteViewModel.insert(route);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
