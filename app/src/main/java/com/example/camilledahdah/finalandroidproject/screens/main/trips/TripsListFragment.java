package com.example.camilledahdah.finalandroidproject.screens.main.trips;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.AuthenticatedScreen;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;
import com.example.camilledahdah.finalandroidproject.data.local.LocalStorageManager;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.example.camilledahdah.finalandroidproject.screens.main.trips.TripsAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Callback;

/**
 * Created by camilledahdah on 4/21/18.
 */

public class TripsListFragment extends BaseFragment implements AuthenticatedScreen {


    private TripsListFragmentListener mListener;

    public static final String TAG = TripsListFragment.class.getSimpleName();

    private AuthenticatedApiManager authenticatedApiManager;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    List<Trip> tripsList;


    public TripsListFragment() {
        // Required empty public constructor
    }

    public static TripsListFragment newInstance() {
        TripsListFragment fragment = new TripsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedApiManager = AuthenticatedApiManager.getInstance(getActivity());
        localStorageManager = LocalStorageManager.getInstance(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAllTrips();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips_list, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    void fetchAllTrips(){

        tripsList = mListener.getTripsList();

//        for (int i = 0; i < 6; i++) {
//
//            tripsList.add(new Trip("4 Kg", "Zouk", new Long(4), "Dahyeh", new Long(3), "I love to eat fish", "Very Large", "Car"));
//        }

        TripsAdapter adapter = new TripsAdapter(getActivity(), (ArrayList<Trip>) tripsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        RecyclerView.LayoutManager mLayoutManager = linearLayoutManager;

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TripsListFragmentListener) {
            mListener = (TripsListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TripsListFragmentListener");
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
            mListener.onErrorFetchingTrips();
        }
    }

    public interface TripsListFragmentListener {
        List<Trip> getTripsList();

        void onErrorFetchingTrips();
    }

}