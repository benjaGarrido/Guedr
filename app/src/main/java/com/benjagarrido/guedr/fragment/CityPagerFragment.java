package com.benjagarrido.guedr.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.model.Cities;
import com.benjagarrido.guedr.model.Forecast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {


    public CityPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        // Accedemos al viewPager de nuestra interfaz
        ViewPager vp = (ViewPager)root.findViewById(R.id.vpCities);

        // Le decimos al viewPager quien es su adaptador, que le dará los fragment que debe dibujar
        vp.setAdapter(new CityPagerAdapter(getFragmentManager()));

        return root;
    }
}

class CityPagerAdapter extends FragmentPagerAdapter {

    private Cities mCities;

    public CityPagerAdapter(FragmentManager fm) {
        super(fm);
        mCities=new Cities();
    }

    @Override
    public Fragment getItem(int position) {
        // Instancio el fragment a mostrar en el viewPager
        ForecastFragment fragment =new ForecastFragment();

        // Saco del modelo la información que necesita el fragment
        String cityName = mCities.getCities().get(position).getName();

        // Le paso al fragment los argumentos que necesita
        Bundle arguments = new Bundle();
        arguments.putString("cityName",cityName);

        // Asigno los argumentos al fragment
        fragment.setArguments(arguments);

        // Devuelvo el fragment listo para ser mostrado
        return fragment;
    }

    @Override
    public int getCount() {
        return mCities.getCities().size();
    }
}
