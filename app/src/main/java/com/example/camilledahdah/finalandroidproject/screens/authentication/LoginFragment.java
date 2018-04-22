package com.example.camilledahdah.finalandroidproject.screens.authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.camilledahdah.finalandroidproject.API.authentication.AuthenticationApiManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.base.BaseFragment;
import com.example.camilledahdah.finalandroidproject.data.local.LocalStorageManager;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.User;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends BaseFragment {

    private LoginFragmentListener listener;
    private AuthenticationApiManager authenticationApiManager;
    private LocalStorageManager localStorageManager;

    @BindView(R.id.login_email_placeholder)
    TextInputLayout emailHolder;

    @BindView(R.id.login_email)
    EditText emailEditText;

    @BindView(R.id.login_password_placeholder)
    TextInputLayout passwordHolder;

    @BindView(R.id.login_password)
    EditText passwordEditText;

    @BindView(R.id.login_button)
    Button loginButton;

    //@BindView(R.id.)
//    TextView registerTextView;

    @BindView(R.id.progress_bar_login)
    ProgressBar progressBar;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationApiManager = AuthenticationApiManager.getInstance();
        localStorageManager = LocalStorageManager.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragmentListener) {
            listener = (LoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement LoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @OnClick(R.id.login_button)
    public void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean flag = true;

        if (TextUtils.isEmpty(email)) {
            emailHolder.setError("email is required");
            flag = false;
        } else {
            emailHolder.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password)) {
            passwordHolder.setError("password is required");
            flag = false;
        } else {
            passwordHolder.setErrorEnabled(false);
        }

        if (flag) {
            showProgressBar();
            User loginUser = new User(email, password);
            authenticationApiManager.login(loginUser).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    hideProgressBar();
                    if (response.isSuccessful()) {
                        User apiUser = response.body();
                        localStorageManager.saveUser(apiUser);
                        listener.onLoginSuccess();
                    } else {
                        try {
                            String errorString = response.errorBody().string();
                            ApiError error = parseApiErrorString(errorString);
                            showToastMessage(error.getMessage());
                            listener.onLoginFailure();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    hideProgressBar();
                    listener.onLoginFailure();
                    showToastMessage(t.getMessage());
                }
            });
        }
    }

    @OnClick(R.id.sign_up_button_login)
    public void requestRegister() {
        listener.onRequestRegister();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface LoginFragmentListener {

        void onLoginSuccess();

        void onLoginFailure();

        void onRequestRegister();
    }
}
