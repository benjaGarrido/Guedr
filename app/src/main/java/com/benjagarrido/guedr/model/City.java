package com.benjagarrido.guedr.model;

/**
 * Created by benjamingarridobarreiro on 11/11/16.
 */

public class City {
    private String mName;
    private Forecast mForecast;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }

    public City(String name, Forecast forecast) {

        mName = name;
        mForecast = forecast;
    }
}
