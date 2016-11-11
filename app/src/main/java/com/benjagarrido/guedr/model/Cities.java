package com.benjagarrido.guedr.model;

import com.benjagarrido.guedr.R;

import java.util.LinkedList;

/**
 * Created by benjamingarridobarreiro on 11/11/16.
 */

public class Cities {
    // Declaramos el array como un genérico para evitar problemas futuros
    private LinkedList<City> mCities;

    public Cities() {
        // Me creo mis ciudades de forma estática
        mCities =  new LinkedList<City>();
        mCities.add(new City("Tokio",new Forecast(23,12,80,"Soleado con nubes", R.drawable.sun_cloud)));
        mCities.add(new City("Jaén",new Forecast(43,32,20,"Calorazo", R.drawable.ico_01)));
        mCities.add(new City("Quito",new Forecast(23,12,80,"Arcoiris", R.drawable.ico_10)));
    }

    public LinkedList<City> getCities() {
        return mCities;
    }
}
