package com.example.camilledahdah.finalandroidproject.models;

import java.util.HashMap;

/**
 * Created by camilledahdah on 4/30/18.
 */

public class TripSearch extends HashMap<String, String> {


    Double weight;
    Long fromDate, toDate;
    String fromLocation, toLocation;

    public TripSearch(Long fromDate, Long toDate, String fromLocation, String toLocation, Double weight){

        this.weight = weight;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;

        put("weight", String.valueOf(weight));
        put("fromDate", String.valueOf(fromDate));
        put("toDate", String.valueOf(toDate));
        put("fromLocation", fromLocation);
        put("toLocation", toLocation);

    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getFromDate() {
        return fromDate;
    }

    public void setFromDate(Long fromDate) {
        this.fromDate = fromDate;
    }

    public Long getToDate() {
        return toDate;
    }

    public void setToDate(Long toDate) {
        this.toDate = toDate;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }


}
