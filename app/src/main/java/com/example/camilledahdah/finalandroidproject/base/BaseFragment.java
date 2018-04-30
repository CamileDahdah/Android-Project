package com.example.camilledahdah.finalandroidproject.base;

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
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void showNoInternetToastMessage(){
        Toast.makeText(getActivity(), "Something went wrong, check that you have internet connection", Toast.LENGTH_SHORT).show();

    }

    public ApiError parseApiErrorString(String error) {
        return gson.fromJson(error, ApiError.class);
    }
    public void actBasedOnApiErrorCode(ApiError apiError) {
        if (apiError.getStatusCode() == 401 && this instanceof AuthenticatedScreen) {
            AuthenticatedScreen screen = (AuthenticatedScreen) this;
            screen.notLoggedInAnymore();
        }
    }


}
