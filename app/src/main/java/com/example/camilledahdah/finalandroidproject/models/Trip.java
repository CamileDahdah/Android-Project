package com.example.camilledahdah.finalandroidproject.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camilledahdah on 4/20/18.
 */

public class Trip {


    @SerializedName("_id")
    private String id;
    //@SerializedName("weight")
    private String weight;
    //@SerializedName("from_location")
    private String fromLocation;
    //@SerializedName("from_date")
    private String fromDate;
    //@SerializedName("to_location")
    private String toLocation;
    //@SerializedName("to_date")
    private String toDate;
    //@SerializedName("observations")
    private String observations;
    //@SerializedName("capacity_volume")
    private String capacityVolume;
    //@SerializedName("transport")
    private String transport;

    public Trip (String weight, String fromLocation, String fromDate, String toLocation, String toDate, String observations, String capacityVolume, String transport) {

        this.weight = weight;
        this.fromLocation = fromLocation;
        this.fromDate = fromDate;
        this.toLocation = toLocation;
        this.toDate = toDate;
        this.observations = observations;
        this.capacityVolume = capacityVolume;
        this.transport = transport;
    }


    public String getWeight() {
        return weight;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToLocation() {
        return toLocation;
    }

    public String getToDate() {
        return toDate;
    }

    public String getObservations() {
        return observations;
    }

    public String getCapacityVolume() {
        return capacityVolume;
    }

    public String getId() {
        return id;
    }

    public String getTransport() {
        return transport;
    }


}
