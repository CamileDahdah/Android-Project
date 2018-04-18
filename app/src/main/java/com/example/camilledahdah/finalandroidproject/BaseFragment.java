package com.example.camilledahdah.finalandroidproject;

import android.app.Fragment;
import android.widget.Toast;

import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.google.gson.Gson;

/**
 * Created by camilledahdah on 4/17/18.
 */

public class BaseFragment extends Fragment {

    private Gson gson = new Gson();

    public void showToastMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public ApiError parseApiErrorString(String error) {
        return gson.fromJson(error, ApiError.class);
    }

}
