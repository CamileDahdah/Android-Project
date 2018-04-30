package com.example.camilledahdah.finalandroidproject.screens.main.trips;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camilledahdah.finalandroidproject.R;
import com.example.camilledahdah.finalandroidproject.models.Trip;

import java.util.ArrayList;

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
        Trip trip = mList.get(position);

        // Set item views based on your views and data model
        TextView tripText = holder.tripText;
        TextView observationsText = holder.observationsText;
        TextView weight = holder.weight;
        TextView volume = holder.volume;
        ImageView phone = holder.phone;
        ImageView profilePic = holder.profilePic;

        String text = "Camile" + " " + "El Dahdah" + " is travelling from " + trip.getFromLocation()
                + " to " + trip.getToLocation() + " on " + trip.getToDate();


        tripText.setText(text);
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

                String phoneNumber = "+96176465311";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                mContext.startActivity(intent);

            }
        });

        //profilePic.setImageResource();
    }


    @Override
    public int getItemCount() {
        return mList.size();
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
