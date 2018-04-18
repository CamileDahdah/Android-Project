package com.example.camilledahdah.finalandroidproject;

/**
 * Created by camilledahdah on 4/17/18.
 */

import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;


import butterknife.BindView;
        import butterknife.ButterKnife;

public class AuthenticationActivity extends AppCompatActivity
        implements RegisterFragment.RegisterFragmentListener, LoginFragment.LoginFragmentListener {

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);
        ButterKnife.bind(this);
        addLoginFragment();
 ;
    }

    @Override
    public void addLoginFragment() {
        LoginFragment fragment = LoginFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onRequestRegister() {
        RegisterFragment fragment = RegisterFragment.newInstance();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, RegisterFragment.TAG)
                .addToBackStack(RegisterFragment.TAG)
                .commit();
    }
    
    @Override
    public void onRegisterSuccess() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onRegisterFailure() {

    }
}
