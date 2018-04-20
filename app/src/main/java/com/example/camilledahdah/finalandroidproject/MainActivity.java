package com.example.camilledahdah.finalandroidproject;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;

import com.example.camilledahdah.finalandroidproject.API.authentication.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    @BindView(R.id.observations_edit_text)
    EditText observationsEditText;

    int transportResID[] = new int[5];
    int capacityResID[] = new int[5];
    ImageView[] transportImages = new ImageView[5];
    ImageView[] capacityImages = new ImageView[5];
//    List<String> transportTextList = Arrays.asList("car", "bus", "train", "truck", "airplane");
//    List<String> capacityTextList = Arrays.asList("car", "bus", "train", "truck", "airplane");
    int currentCapacityIndex, currentTransportIndex;
    public static final String TAG = MainActivity.class.getSimpleName();

    private AuthenticatedApiManager authenticatedApiManager;

    final String meansOfTransport = "means_of_transport";
    final String capacityVolume = "capacity_volume";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_trip);

        authenticatedApiManager = AuthenticatedApiManager.getInstance(this);

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

        currentCapacityIndex = 0;
        currentTransportIndex = 0;

    }

    @OnClick(R.id.from_date)
    public void clickFromDate(){
        DataDialogManager.clickDate(fromDateButton, this);
    }

    @OnClick(R.id.to_date)
    public void clickToDate(){

        DataDialogManager.clickDate(toDateButton, this);

    }



    @Optional
    @OnClick({R.id.means_of_transport1, R.id.means_of_transport2, R.id.means_of_transport3, R.id.means_of_transport4, R.id.means_of_transport5})
    public void onClickTransport(View view) {

        for(int i = 0; i < transportResID.length; i++){

            if(view.getId() == transportResID[i]) {
                view.setBackgroundColor(Color.BLUE);
                currentTransportIndex = i;

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
                currentCapacityIndex = i;

            }else{

                capacityImages[i].setBackground(getResources().getDrawable(R.drawable.border_image));
            }

        }
    }

    @OnClick(R.id.post_trip_button)
    public void onClickPostTrip(){

        String fromLocation = fromCountry.getText().toString();
        String fromDate = fromDateButton.getText().toString();
        String toLocation = toCountry.getText().toString();
        String toDate = toDateButton.getText().toString();

        String observations = observationsEditText.getText().toString();
        String capacityVolume = currentCapacityIndex + "";
        String transportIndex = currentTransportIndex + "";
        String weight = weightSpinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(fromLocation) && !TextUtils.isEmpty(fromDate) && !TextUtils.isEmpty(toLocation) && !TextUtils.isEmpty(toDate)
        && !TextUtils.isEmpty(capacityVolume) && !TextUtils.isEmpty(transportIndex) && !TextUtils.isEmpty(weight)) {

            Trip trip = new Trip(weight, fromLocation, fromDate, toLocation, toDate, observations, capacityVolume, transportIndex);
            authenticatedApiManager.createTrip(trip).enqueue(new Callback<List<Trip>>() {

                @Override
                public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "success!");
                    } else {
                        try {
                            String errorJson = response.errorBody().string();
                            ApiError apiError = new Gson().fromJson(errorJson, ApiError.class);
                            Log.d(TAG, String.valueOf(apiError));
                            //actBasedOnApiErrorCode(apiError);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Trip>> call, Throwable t) {

                }
            });
        }
    }




}
