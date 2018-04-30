package com.example.camilledahdah.finalandroidproject.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by camilledahdah on 4/20/18.
 */

public class Trip {


    @SerializedName("_id")
    private String id;
    @SerializedName("weight")
    private Double weight;
    @SerializedName("from_location")
    private String fromLocation;
    @SerializedName("from_date")
    private Long fromDate;
    @SerializedName("to_location")
    private String toLocation;
    @SerializedName("to_date")
    private Long toDate;
    @SerializedName("observations")
    private String observations;
    @SerializedName("capacity_volume")
    private String capacityVolume;
    @SerializedName("transport")
    private String transport;

    public Trip (Double weight, String fromLocation, Long fromDate, String toLocation, Long
            toDate, String observations, String capacityVolume, String transport) {

        this.weight = weight;
        this.fromLocation = fromLocation;
        this.fromDate = fromDate;
        this.toLocation = toLocation;
        this.toDate = toDate;
        this.observations = observations;
        this.capacityVolume = capacityVolume;
        this.transport = transport;
    }


    public Double getWeight() {
        return weight;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public Long getFromDate() {
        return fromDate;
    }

    public String getToLocation() {
        return toLocation;
    }

    public Long getToDate() {
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
