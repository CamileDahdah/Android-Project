package com.example.camilledahdah.finalandroidproject;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;


public class MainActivity extends AppCompatActivity{

    @BindView(R.id.from_date)
    public Button fromDateButton;

    @BindView(R.id.to_date)
    public Button toDateButton;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.from_country)
    TextView fromCountry;

    @BindView(R.id.to_country)
    TextView toCountry;

    @BindView(R.id.weight_spinner)
    Spinner weightSpinner;

    int transportResID[] = new int[5];
    int capacityResID[] = new int[5];
    ImageView[] transportImages = new ImageView[5];
    ImageView[] capacityImages = new ImageView[5];

    public static final String TAG = MainActivity.class.getSimpleName();

    final String meansOfTransport = "means_of_transport";
    final String capacityVolume = "capacity_volume";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_trip);

        ButterKnife.bind(this);
        CountryHandler countryHandler = new CountryHandler(recyclerView, fromCountry, this);
        countryHandler.addCountryListener();

        CountryHandler countryHandler2 = new CountryHandler(recyclerView, toCountry, this);
        countryHandler2.addCountryListener();

        String[] items = new String[]{ "0.5 Kg", "1 Kg", "2 Kg", "5 Kg", "10 Kg","+20 Kg" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        weightSpinner.setAdapter(adapter);

        for(int i = 0; i < 5; i++) {
            transportResID[i] = getResources().getIdentifier(meansOfTransport + (i + 1), "id", getPackageName());
            capacityResID[i] = getResources().getIdentifier(capacityVolume + (i + 1), "id", getPackageName());
            transportImages[i] = findViewById(transportResID[i]);
            capacityImages[i] = findViewById(capacityResID[i]);
        }
    }
    @OnClick(R.id.from_date)
    public void clickFromDate(){
        clickDate(fromDateButton);
    }

    @OnClick(R.id.to_date)
    public void clickToDate(){

        clickDate(toDateButton);

    }

    public void clickDate(final Button dateTextButton){
        Calendar cal = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        }.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d("date", "onDateSet: dd/mm/yy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dateTextButton.setText(date);

            }
        }
                ,year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    @Optional
    @OnClick({R.id.means_of_transport1, R.id.means_of_transport2, R.id.means_of_transport3, R.id.means_of_transport4, R.id.means_of_transport5})
    public void onClickTransport(View view) {

        for(int i = 0; i < transportResID.length; i++){

            if(view.getId() == transportResID[i]) {
                view.setBackgroundColor(Color.BLUE);


            }else{

                transportImages[i].setBackground(getResources().getDrawable(R.drawable.border_image));
            }

        }
    }


    @Optional
    @OnClick({R.id.capacity_volume1, R.id.capacity_volume2, R.id.capacity_volume3, R.id.capacity_volume4, R.id.capacity_volume5})
    public void onClickCapacity(View view) {

        for(int i = 0; i < capacityResID.length; i++){

            if(view.getId() == capacityResID[i]) {
                view.setBackgroundColor(Color.BLUE);


            }else{

                capacityImages[i].setBackground(getResources().getDrawable(R.drawable.border_image));
            }

        }
    }

}
