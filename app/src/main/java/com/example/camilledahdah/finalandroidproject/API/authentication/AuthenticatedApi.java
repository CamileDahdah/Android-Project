package com.example.camilledahdah.finalandroidproject.API.authentication;

import com.example.camilledahdah.finalandroidproject.models.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by camilledahdah on 4/20/18.
 */

public interface AuthenticatedApi {

    @POST("trips")
    Call<List<Trip>> createNewTrip(@Body Trip trip);

}
