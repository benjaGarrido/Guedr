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
        mCities.add(new City("Tokio"));
        mCities.add(new City("Jaén"));
        mCities.add(new City("Quito"));
    }

    public LinkedList<City> getCities() {
        return mCities;
    }
}
