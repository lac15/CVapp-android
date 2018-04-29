package com.example.lac.cvapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Address implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "zip_code")
    private int zipCode;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "house_number")
    private String houseNumber;

    public Address() {
    }

    public Address(String country, int zipCode, String state, String city, String street, String houseNumber) {
        this.country = country;
        this.zipCode = zipCode;
        this.state = state;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
