package com.benjagarrido.guedr.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.model.Cities;
import com.benjagarrido.guedr.model.City;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityListFragment extends Fragment {
    private CityListListener mCityListListener;

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
        final Cities cities = new Cities();

        // Creamos un adaptador para rellenar dárselo a la lista y que sepa que datos mostrar
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getActivity(),
                android.R.layout.simple_list_item_1,
                cities.getCities());

        // Le asignamos el adaptador a la vista
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aviso a mi listener
                if (mCityListListener != null){
                    // Aquí puedo avisar a mi listener. Tengo que sacar la ciudad que se ha pulsado
                    City city = cities.getCities().get(position);
                    mCityListListener.onCitySelected(city,position);
                }
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCityListListener = (CityListListener) getActivity();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCityListListener = (CityListListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCityListListener = null;
    }

    public interface CityListListener {
        void onCitySelected (City city, int position);
    }
}
