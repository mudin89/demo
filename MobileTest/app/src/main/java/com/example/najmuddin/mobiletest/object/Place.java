package com.example.najmuddin.mobiletest.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Najmuddin on 30/11/2016.
 */

public class Place {

    @SerializedName("area")
    String Area;
    @SerializedName("city")
    String City;

    public Place(){

    }

    public Place(String area,String city){
        Area=area;
        City=city;
    }

    public String getArea() {
        return Area;
    }

    public String getCity() {
        return City;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setCity(String city) {
        City = city;
    }
}
