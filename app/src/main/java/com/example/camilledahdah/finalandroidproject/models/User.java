package com.example.camilledahdah.finalandroidproject.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by camilledahdah on 4/17/18.
 */

public class User {

    @SerializedName("id")
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String token;
    private String phoneNumber;
    private String country;
    private String city;

    private List<Trip> tripsList;

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String city, String country) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.country = country;

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Trip> getTripsList() {
        return tripsList;
    }

    public void setTripsList(List<Trip> tripsList) {
        this.tripsList = tripsList;
    }

}
