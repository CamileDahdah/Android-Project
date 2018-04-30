package com.example.camilledahdah.finalandroidproject.screens.main.trips;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApi;
import com.example.camilledahdah.finalandroidproject.API.authenticated.AuthenticatedApiManager;
import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.models.ApiError;
import com.example.camilledahdah.finalandroidproject.models.Trip;
import com.example.camilledahdah.finalandroidproject.models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by camilledahdah on 4/21/18.
 */



public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Trip> mList;

    public TripsAdapter(Context context, ArrayList<Trip> list){
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.trip_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Trip trip = mList.get(position);

        // Set item views based on your views and data model
        final TextView tripText = holder.tripText;
        TextView observationsText = holder.observationsText;
        TextView weight = holder.weight;
        TextView volume = holder.volume;
        ImageView phone = holder.phone;
        ImageView profilePic = holder.profilePic;
        final String[] phoneNumber = new String[1];

        phoneNumber[0] = null;


        AuthenticatedApiManager.getInstance(mContext).getSpecificProfile(trip.getEmail()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    User user = response.body();

                    String text = user.getFirstName() + " " + user.getLastName() + " is travelling from " + trip.getFromLocation() + " on " + getDate(trip.getFromDate())
                            + " to " + trip.getToLocation() + " on " + getDate(trip.getToDate());

                    phoneNumber[0] = user.getPhoneNumber();
                    tripText.setText(text);

                } else {
                    try {
                        String errorJson = response.errorBody().string();
                        ApiError apiError = new Gson().fromJson(errorJson, ApiError.class);

                        //actBasedOnApiErrorCode(apiError);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


            }
        });





        observationsText.setText(trip.getObservations());

        if(trip.getWeight() != Double.MAX_VALUE) {
            weight.setText(String.valueOf(trip.getWeight()) + " Kg");
        }else{

            weight.setText("+11 Kg");
        }

        volume.setText(trip.getCapacityVolume());



        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phoneNumber[0] != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber[0], null));
                    mContext.startActivity(intent);
                }

            }
        });

        //profilePic.setImageResource();
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    @Override
    public int getItemCount() {
        if(mList != null) {
            return mList.size();
        }else{
            return 0;

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tripText;
        TextView observationsText;
        TextView weight;
        TextView volume;
        ImageView phone;
        ImageView profilePic;


        public ViewHolder(View itemView) {
            super(itemView);


            tripText = itemView.findViewById(R.id.trip_text);
            observationsText = itemView.findViewById(R.id.trip_observations);
            weight = itemView.findViewById(R.id.weight_trip);
            volume = itemView.findViewById(R.id.volume_trip);
            phone = itemView.findViewById(R.id.phone);
            profilePic = itemView.findViewById(R.id.profile_pic);


        }
    }





}
