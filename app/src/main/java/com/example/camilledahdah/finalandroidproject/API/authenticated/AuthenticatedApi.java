package com.example.camilledahdah.finalandroidproject.API.authenticated;

import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.example.camilledahdah.finalandroidproject.models.TripSearch;
import com.example.camilledahdah.finalandroidproject.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by camilledahdah on 4/20/18.
 */

public interface AuthenticatedApi {

    @GET("profile")
    Call<User> getProfile();

    @GET("profile/search")
    Call<User> getSpecificProfile( @Query("email") String email);

    @POST("trips")
    Call<User> createNewTrip(@Body Trip trip);

    @GET("trips/search")
    Call<List<Trip>> getTrips(@QueryMap TripSearch tripSearch);

}
