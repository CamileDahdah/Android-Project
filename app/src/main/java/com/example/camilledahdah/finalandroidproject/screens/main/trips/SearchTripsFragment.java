package com.example.camilledahdah.finalandroidproject.screens.main.trips;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.CountryHandler;
import com.example.camilledahdah.finalandroidproject.DataDialogManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.AuthenticatedScreen;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private AuthenticatedApiManager authenticatedApiManager;

    private SearchTripsFragmentListener mListener;


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

        return view;
    }

    @OnClick(R.id.time_button)
    public void onClickTimeButton(){

        DataDialogManager.clickDate(timeButton, getActivity());

    }

    @OnClick(R.id.search_match_button)
    public void onClickSearchMatch(){



    }

    @Override
    public void notLoggedInAnymore() {
        if (mListener != null) {
            //mListener.onNewEvertCreationFailure();
        }
    }


    public interface SearchTripsFragmentListener {


    }

}
