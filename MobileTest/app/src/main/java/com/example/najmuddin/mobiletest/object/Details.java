package com.example.najmuddin.mobiletest.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class Details {
    @SerializedName("id")
    int Id;
    @SerializedName("name")
    String Name;
    @SerializedName("speciality")
    String Speciality;
    @SerializedName("area")
    String Area;
    @SerializedName("currency")
    String Currency;
    @SerializedName("rate")
    int Rate;
    @SerializedName("recommendation")
    int Recommendation;
    @SerializedName("schedule")
    String Schedule;
    @SerializedName("experience")
    String Experience;
    @SerializedName("latitude")
    double Latitude;
    @SerializedName("longitute")
    double Longtitude;
    @SerializedName("photo")
    String Photo;
    @SerializedName("description")
    String Description;


    public Details(){

    }

    public Details(int id, String name,String speciality,String area,String currency,int rate,
                   int recommendation,String schedule,String experience,long latitude,long longtitude,
                   String photo,String description){
        Id=id;
        Name=name;
        Speciality=speciality;
        Area=area;
        Currency=currency;
        Rate=rate;
        Recommendation=recommendation;
        Schedule=schedule;
        Experience=experience;
        Latitude=latitude;
        Longtitude=longtitude;
        Photo=photo;
        Description=description;

    }

    public int getRate() {
        return Rate;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getArea() {
        return Area;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongtitude() {
        return Longtitude;
    }

    public String getDescription() {
        return Description;
    }

    public String getExperience() {
        return Experience;
    }

    public String getPhoto() {
        return Photo;
    }

    public int getRecommendation() {
        return Recommendation;
    }

    public String getSchedule() {
        return Schedule;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        Longtitude = longtitude;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public void setRecommendation(int recommendation) {
        Recommendation = recommendation;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }
}
