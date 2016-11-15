package com.benjagarrido.guedr.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.model.Cities;
import com.benjagarrido.guedr.model.City;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment {


    public CityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_list, container, false);

        // Accedo al listview
        ListView list = (ListView) root.findViewById(R.id.lvCities);

        // Necesito un modelo con el que darle valores a la lista
        Cities cities = new Cities();

        // Creamos un adaptador para rellenar dárselo a la lista y que sepa que datos mostrar
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getActivity(),
                android.R.layout.simple_list_item_1,
                cities.getCities());

        // Le asignamos el adaptador a la vista
        list.setAdapter(adapter);

        return root;
    }

}
