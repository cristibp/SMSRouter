package com.example.android.router;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_ROUTE_ACTIVITY_REQUEST_CODE = 1;

    private RouteViewModel mRouteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RouteListAdapter adapter = new RouteListAdapter(new RouteListAdapter.RouteDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mRouteViewModel = new ViewModelProvider(this).get(RouteViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mRouteViewModel.getAllRoutes().observe(this, routes -> {
            // Update the cached copy of the routes in the adapter.
            adapter.submitList(routes);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewRouteActivity.class);
            startActivityForResult(intent, NEW_ROUTE_ACTIVITY_REQUEST_CODE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_ROUTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Route route = new Route(data.getStringExtra(NewRouteActivity.EXTRA_REPLY_URL), data.getStringExtra(NewRouteActivity.EXTRA_REPLY_PHONE_NUMBER), true);
            mRouteViewModel.insert(route);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
