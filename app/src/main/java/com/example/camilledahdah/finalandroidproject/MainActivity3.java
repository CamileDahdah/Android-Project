package com.example.camilledahdah.finalandroidproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.camilledahdah.finalandroidproject.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camilledahdah on 4/21/18.
 */

public class MainActivity3 extends  AppCompatActivity {


    RecyclerView recyclerView;

    ArrayList<Trip> tripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trips_list);

        recyclerView = findViewById(R.id.rv);




        tripsList = new ArrayList<>();


        for (int i = 0; i < 6; i++) {

            tripsList.add(new Trip("4 Kg", "Zouk", "November", "Dahyeh", "tomorrow", "I love to eat fish", "Very Large", "Car"));

        }


        TripsAdapter adapter = new TripsAdapter(this, tripsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }


}