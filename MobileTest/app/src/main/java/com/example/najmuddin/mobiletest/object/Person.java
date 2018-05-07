package com.example.najmuddin.mobiletest.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class Person {

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
    @SerializedName("photo")
    String Photolink;

    public Person(){

    }

    public Person(int id, String name,String speciality,String area,String currency,int rate,String photolink){
        Id=id;
        Name=name;
        Speciality=speciality;
        Area=area;
        Currency=currency;
        Rate=rate;
        Photolink=photolink;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public String getArea() {
        return Area;
    }

    public String getCurrency() {
        return Currency;
    }

    public int getRate() {
        return Rate;
    }

    public String getPhotolink() {
        return Photolink;
    }
}
