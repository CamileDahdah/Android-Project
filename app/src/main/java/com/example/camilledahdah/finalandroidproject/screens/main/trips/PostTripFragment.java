package com.example.camilledahdah.finalandroidproject.screens.main.trips;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.models.User;
import com.example.camilledahdah.finalandroidproject.utils.CountryHandler;
import com.example.camilledahdah.finalandroidproject.utils.DataDialogManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.AuthenticatedScreen;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostTripFragment extends BaseFragment implements AuthenticatedScreen {

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

    @BindView(R.id.travelling_by_textview)
    TextView travellingByTextView;

    @BindView(R.id.item_volume_textview)
    TextView itemVolumeTextView;

    @BindView(R.id.weight_spinner)
    Spinner weightSpinner;

    @BindView(R.id.observations_edit_text)
    EditText observationsEditText;

    private LocationFragmentListener mListener;

    String travellingByText = "You are travelling by";
    String itemVolumeText = "Item Volume is";

    Long fromDateLong, toDateLong;

    int backgroundButtonColor;

    int transportResID[] = new int[5];
    int capacityResID[] = new int[5];
    ImageView[] transportImages = new ImageView[5];
    ImageView[] capacityImages = new ImageView[5];

    List<String> transportTextList = Arrays.asList("Car", "Bus", "Train", "Truck", "Airplane");
    List<String> capacityTextList = Arrays.asList("X Small", "Small", "Medium", "Large", "X Large");

    int currentCapacityIndex, currentTransportIndex;
    public static final String TAG = PostTripFragment.class.getSimpleName();

    private AuthenticatedApiManager authenticatedApiManager;

    final String meansOfTransport = "means_of_transport";
    final String capacityVolume = "capacity_volume";


    public PostTripFragment() {
        // Required empty public constructor
    }

    public static PostTripFragment newInstance() {
        PostTripFragment fragment = new PostTripFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedApiManager = AuthenticatedApiManager.getInstance(getActivity());
        backgroundButtonColor = Color.parseColor("#21af4b");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.post_trip, container, false);

        fromDateLong = toDateLong = null;
        ButterKnife.bind(this, view);

        itemVolumeTextView.setText(itemVolumeText);
        travellingByTextView.setText(travellingByText);

        CountryHandler countryHandler = new CountryHandler(recyclerView, fromCountry, getActivity());
        countryHandler.addCountryListener();

        CountryHandler countryHandler2 = new CountryHandler(recyclerView, toCountry, getActivity());
        countryHandler2.addCountryListener();

        String[] items = new String[]{ "0.5 Kg", "1 Kg", "2 Kg", "5 Kg", "10 Kg","+11 Kg" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        weightSpinner.setAdapter(adapter);

        for(int i = 0; i < 5; i++) {
            transportResID[i] = getResources().getIdentifier(meansOfTransport + (i + 1), "id", getActivity().getPackageName());
            capacityResID[i] = getResources().getIdentifier(capacityVolume + (i + 1), "id", getActivity().getPackageName());
            transportImages[i] = view.findViewById(transportResID[i]);
            capacityImages[i] = view.findViewById(capacityResID[i]);
        }

        currentCapacityIndex = 0;
        currentTransportIndex = 0;



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LocationFragmentListener) {
            mListener = (LocationFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LocationFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void notLoggedInAnymore() {
        if (mListener != null) {
            mListener.onNewTripCreationFailure();
        }
    }

    public interface LocationFragmentListener {
        void onNewTripCreatedSuccessfully();

        void onNewTripCreationFailure();
    }


    @OnClick(R.id.from_date)
    public void clickFromDate(){
        fromDateLong = DataDialogManager.clickDate(fromDateButton, getActivity());

    }

    @OnClick(R.id.to_date)
    public void clickToDate(){

        toDateLong = DataDialogManager.clickDate(toDateButton, getActivity());

    }



    @Optional
    @OnClick( {R.id.means_of_transport1, R.id.means_of_transport2, R.id.means_of_transport3, R.id.means_of_transport4, R.id.means_of_transport5})
    public void onClickTransport(View view) {

        for(int i = 0; i < transportResID.length; i++){

            if(view.getId() == transportResID[i]) {
                travellingByTextView.setText(travellingByText + " " + transportTextList.get(i));
                view.setBackgroundColor(backgroundButtonColor);
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
                itemVolumeTextView.setText(itemVolumeText + " " + capacityTextList.get(i));
                view.setBackgroundColor(backgroundButtonColor);
                currentCapacityIndex = i;

            }else{
                capacityImages[i].setBackground(getResources().getDrawable(R.drawable.border_image));
            }

        }
    }

    @OnClick(R.id.post_trip_button)
    public void onClickPostTrip(){

        String fromLocation = fromCountry.getText().toString();

        String toLocation = toCountry.getText().toString();


        String observations = observationsEditText.getText().toString();
        String capacityVolume = currentCapacityIndex + "";
        String transportIndex = currentTransportIndex + "";

        String weight = weightSpinner.getSelectedItem().toString();

        Double weightDouble = null;

        try {

            weightDouble = Double.parseDouble(weight.replaceAll("[/[^0-9.]/g, \"\"]", ""));
            if(weightDouble == 11){
                weightDouble = Double.MAX_VALUE;

            }

        }catch (Exception e){

            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(fromLocation) && fromDateLong != null && !TextUtils.isEmpty(toLocation) && toDateLong != null
                && !TextUtils.isEmpty(capacityVolume) && !TextUtils.isEmpty(transportIndex) && weightDouble != null) {

            Trip trip = new Trip(weightDouble, fromLocation, fromDateLong.longValue(), toLocation, toDateLong.longValue(), observations, capacityTextList.get(currentCapacityIndex), transportTextList.get(currentTransportIndex));
            authenticatedApiManager.createTrip(trip).enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        showToastMessage(String.valueOf("success!"));
                    } else {
                        try {
                            String errorJson = response.errorBody().string();
                            ApiError apiError = new Gson().fromJson(errorJson, ApiError.class);
                            showToastMessage(String.valueOf(apiError));
                            //actBasedOnApiErrorCode(apiError);
                        } catch (IOException e) {
                            e.printStackTrace();
                            showToastMessage("Error: " + e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    showToastMessage(t.getMessage());
                }
            });
        }else{

            showToastMessage("Missing Fields :/");

        }
    }




}
