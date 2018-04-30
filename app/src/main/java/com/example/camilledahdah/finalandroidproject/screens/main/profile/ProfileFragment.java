package com.example.camilledahdah.finalandroidproject.screens.main.profile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.AuthenticatedScreen;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;
import com.example.camilledahdah.finalandroidproject.data.local.LocalStorageManager;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends BaseFragment implements AuthenticatedScreen{

    private ProfileFragmentListener listener;

    private AuthenticatedApiManager authenticatedApi;
    private User user;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.name)
    TextView nameTextView;

    @BindView(R.id.email)
    TextView emailTextView;

    @BindView(R.id.phone_number)
    TextView phoneNumberTextView;

    @BindView(R.id.country)
    TextView countyTextView;

    @BindView(R.id.city)
    TextView cityTextView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {

        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localStorageManager = LocalStorageManager.getInstance(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        authenticatedApi = AuthenticatedApiManager.getInstance(getActivity());

        authenticatedApi.getProfile().enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    showUserDetails(user);
                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        ApiError apiError = parseApiErrorString(errorJson);
                        actBasedOnApiErrorCode(apiError);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                fetchUserFromLocalStorage();
            }
        });
    }

    private void showUserDetails(User user) {
        if (!TextUtils.isEmpty(user.getFirstName())) {

            if(!TextUtils.isEmpty(user.getLastName())){
                nameTextView.setText(user.getFirstName() + " " + user.getLastName());
            }else{

                nameTextView.setText(user.getFirstName());
            }
        }
        if (!TextUtils.isEmpty(user.getEmail())) {
            emailTextView.setText(user.getEmail());
        }
        if (!TextUtils.isEmpty(user.getPhoneNumber())) {
            phoneNumberTextView.setText(user.getPhoneNumber());
        }
        if (!TextUtils.isEmpty(user.getCountry())) {
            countyTextView.setText(user.getCountry());
        }
        if (!TextUtils.isEmpty(user.getCity())) {
            cityTextView.setText(user.getCity());
        }

    }

    private void fetchUserFromLocalStorage() {
        User user = localStorageManager.getUser();
        if (user != null) {
            showUserDetails(user);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ProfileFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void notLoggedInAnymore() {
        localStorageManager.deleteUser();
        listener.onProfileFetchFailure();
    }

    public interface ProfileFragmentListener {
        void onProfileFetchFailure();
    }

}
