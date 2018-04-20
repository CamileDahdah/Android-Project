package com.example.camilledahdah.finalandroidproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.from_country)
    TextView fromCountry;

    @BindView(R.id.to_country)
    TextView toCountry;

    @BindView(R.id.time_button)
    Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_trips);

        ButterKnife.bind(this);
        CountryHandler countryHandler = new CountryHandler(recyclerView, fromCountry, this);
        countryHandler.addCountryListener();

        CountryHandler countryHandler2 = new CountryHandler(recyclerView, toCountry, this);
        countryHandler2.addCountryListener();


    }

    @OnClick(R.id.time_button)
    public void onClickTimeButton(){

        DataDialogManager.clickDate(timeButton, this);

    }

    @OnClick(R.id.search_match_button)
    public void onClickSearchMatch(){



    }

}
