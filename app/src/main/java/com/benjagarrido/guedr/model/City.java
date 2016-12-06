package com.benjagarrido.guedr.model;

import java.io.Serializable;

/**
 * Created by benjamingarridobarreiro on 11/11/16.
 */

public class City implements Serializable{
    private String mName;
    private Forecast mForecast;

    public City(String name, Forecast forecast) {
        mName = name;
        mForecast = forecast;
    }
    public City(String name) {
        mName = name;
    }


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

    @Override
    public String toString() {
        return getName();
    }
}
