package com.example.camilledahdah.finalandroidproject.screens.main.trips;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.utils.CountryHandler;
import com.example.camilledahdah.finalandroidproject.utils.DataDialogManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.AuthenticatedScreen;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.example.camilledahdah.finalandroidproject.models.TripSearch;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTripsFragment extends BaseFragment implements AuthenticatedScreen {

    public static final String TAG = SearchTripsFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.from_country)
    TextView fromCountry;

    @BindView(R.id.to_country)
    TextView toCountry;

    @BindView(R.id.time_button)
    Button timeButton;

    @BindView(R.id.weight_spinner)
    Spinner weightSpinner;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AuthenticatedApiManager authenticatedApiManager;

    private SearchTripsFragmentListener mListener;

    Long[] toDate = new Long[1];

    public SearchTripsFragment() {
        // Required empty public constructor
    }

    public static SearchTripsFragment newInstance() {
        SearchTripsFragment fragment = new SearchTripsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedApiManager = AuthenticatedApiManager.getInstance(getActivity());
       // toDate = null;
        toDate[0] = new Long(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_trips, container, false);

        ButterKnife.bind(this, view);

        CountryHandler countryHandler = new CountryHandler(recyclerView, fromCountry, getActivity());
        countryHandler.addCountryListener();

        CountryHandler countryHandler2 = new CountryHandler(recyclerView, toCountry, getActivity());
        countryHandler2.addCountryListener();

        String[] items = new String[]{ "0.5 Kg", "1 Kg", "2 Kg", "5 Kg", "10 Kg","+20 Kg" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        weightSpinner.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.time_button)
    public void onClickTimeButton(){

         DataDialogManager.clickDate(timeButton, getActivity(), toDate);

    }

    @OnClick(R.id.search_match_button)
    public void onClickSearchMatch(){

        String fromCountryText = fromCountry.getText().toString();
        String toCountryText = toCountry.getText().toString();
        String toDateText = null;

        if(toDate!= null) {
            toDateText = String.valueOf(toDate);
        }
        String selectedItem = weightSpinner.getSelectedItem().toString();
        Double selectedItemDouble = null;
        try {

            selectedItemDouble = Double.parseDouble(selectedItem.replaceAll("[/[^0-9.]/g, \"\"]", ""));

            if(selectedItemDouble == 20){
                selectedItemDouble = Double.MAX_VALUE;

            }

        }catch (Exception e){

            e.printStackTrace();
        }

        if(!TextUtils.isEmpty(fromCountryText) && !TextUtils.isEmpty(toCountryText)
                && toDate !=null   && toDate[0] != null && toDate[0] != 0 && selectedItemDouble != null) {

            //No need for from date
            TripSearch tripSearch = new TripSearch(toDate[0], toDate[0], fromCountryText, toCountryText, selectedItemDouble);

            showProgressBar();

            mListener.onFoundTrips(null);

            authenticatedApiManager.getTrips(tripSearch).enqueue(new Callback<List<Trip>>() {

                @Override
                public void onResponse(Call<List<Trip>> call, Response<List<Trip>> response) {

                    hideProgressBar();

                    if (response.isSuccessful()) {

                        List<Trip> tripsList = response.body();

                        if(tripsList == null || tripsList.size() <= 0){
                            showToastMessage("No trips found :/ ");

                        }else{

                            mListener.onFoundTrips(tripsList);
                        }


                    } else {
                        try {
                            String errorJson = response.errorBody().string();
                            ApiError apiError = new Gson().fromJson(errorJson, ApiError.class);
                            showToastMessage(String.valueOf(apiError));
                            //actBasedOnApiErrorCode(apiError);
                        } catch (IOException e) {
                            showToastMessage("Error: " + e);
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Trip>> call, Throwable t) {
                    hideProgressBar();

                    showToastMessage(t.getMessage());
                }
            });
        }else{

            showToastMessage("Missing Fields :/");

        }

    }

    @Override
    public void notLoggedInAnymore() {

        if (mListener != null) {
            //mListener.onNewTripCreationFailure();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchTripsFragmentListener) {
            mListener = (SearchTripsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchTripsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    public interface SearchTripsFragmentListener {

        void onFoundTrips(List<Trip> tripsList);

    }

}
