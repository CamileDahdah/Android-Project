package com.example.camilledahdah.finalandroidproject;

/**
 * Created by camilledahdah on 4/17/18.
 */


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.camilledahdah.finalandroidproject.API.authentication.AuthenticationApiManager;
import com.example.camilledahdah.finalandroidproject.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

public class RegisterFragment extends BaseFragment {

    public static final String TAG = RegisterFragment.class.getSimpleName();



    private AuthenticationApiManager authenticationApiManager;

    private RegisterFragmentListener listener;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.first_name_holder)
    TextInputLayout firstNameHolder;

    @BindView(R.id.first_name)
    EditText firstNameEditText;

    @BindView(R.id.last_name_holder)
    TextInputLayout lastNameHolder;

    @BindView(R.id.last_name)
    EditText lastNameText;

    @BindView(R.id.phone_number)
    EditText phoneNumberText;

    @BindView(R.id.phone_number_holder)
    TextInputLayout phoneNumberHolder;

    @BindView(R.id.city)
    EditText cityText;

    @BindView(R.id.city_holder)
    TextInputLayout cityHolder;

    @BindView(R.id.confirm_password)
    EditText confirmPasswordText;

    @BindView(R.id.confirm_password_placeholder)
    TextInputLayout confirmPasswordHolder;

    @BindView(R.id.country_holder)
    TextInputLayout countryHolder;

    @BindView(R.id.country)
    EditText countryText;

    @BindView(R.id.email_placeholder)
    TextInputLayout emailHolder;

    @BindView(R.id.email)
    EditText emailEditText;

    @BindView(R.id.password_placeholder)
    TextInputLayout passwordHolder;

    @BindView(R.id.password)
    EditText passwordEditText;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;




    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationApiManager = AuthenticationApiManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        ButterKnife.bind(this, view);

        CountryHandler countryHandler = new CountryHandler(recyclerView, countryText, getActivity().getBaseContext());
        countryHandler.addCountryListener();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RegisterFragmentListener) {
            listener = (RegisterFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.sign_up_button)
    public void attemptRegister() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordText.getText().toString().trim();
        String country = countryText.getText().toString().trim();
        String city = cityText.getText().toString().trim();
        String phoneNumber = phoneNumberText.getText().toString().trim();

        boolean flag = true, passwordFlag = true;

        if (TextUtils.isEmpty(firstName)) {
            firstNameHolder.setError("first name field is required");
            flag = false;
        } else {
            firstNameHolder.setError(null);
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameHolder.setError("last name field is required");
            flag = false;
        } else {
            lastNameHolder.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordHolder.setError("confirm password field is required");
            flag = false;
        } else {
            confirmPasswordHolder.setError(null);
        }

        if (TextUtils.isEmpty(city)) {
            cityHolder.setError("city field is required");
            flag = false;
        } else {
            cityHolder.setError(null);
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberHolder.setError("phone number field is required");
            flag = false;
        } else {
            phoneNumberHolder.setError(null);
        }

        if (TextUtils.isEmpty(country)) {
            countryHolder.setError("country field is required");
            flag = false;
        } else {
            countryHolder.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email field is required");
            flag = false;
        } else {
            emailHolder.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password field is required");
            flag = false;
            passwordFlag = false;
        } else {
            passwordHolder.setError(null);
        }

        if(passwordFlag && !password.equals(confirmPassword)){
            flag = false;
            confirmPasswordHolder.setError("passwords don't match");
        }

        if (flag) {
            showProgressBar();
            User registerUser = new User(firstName, lastName, email, password, phoneNumber, city, country);
            authenticationApiManager
                    .register(registerUser)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            hideProgressBar();
                            if (response.isSuccessful()) {
                                listener.onRegisterSuccess();
                            } else {
                                listener.onRegisterFailure();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            hideProgressBar();
                            listener.onRegisterFailure();
                            showToastMessage(t.getMessage());
                        }
                    });
        }
    }

    @OnClick(R.id.country_holder)
    public void search() {
        recyclerView.setVisibility(View.VISIBLE);

    }

    @OnFocusChange(R.id.country)
    public void onFocusChange(boolean hasFocus) {

        if (hasFocus) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }



    @OnClick(R.id.login_button)
    public void goToLoginFragment() {
        listener.addLoginFragment();

    }


    public interface RegisterFragmentListener {

        void onRegisterSuccess();

        void onRegisterFailure();

        void addLoginFragment();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }




}
