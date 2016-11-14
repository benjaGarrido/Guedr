package com.benjagarrido.guedr.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.model.Cities;
import com.benjagarrido.guedr.model.City;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityPagerFragment extends Fragment {
    private Cities mCities;
    private ViewPager mViewPager;

    public CityPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_city_pager, container, false);

        // Recupero el modelo
        mCities = new Cities();

        // Accedemos al viewPager de nuestra interfaz
        mViewPager = (ViewPager)root.findViewById(R.id.vpCities);

        // Le decimos al viewPager quien es su adaptador, que le dará los fragment que debe dibujar
        mViewPager.setAdapter(new CityPagerAdapter(getFragmentManager(), mCities));

        // Capturo el momento en el que usuario cambia de pagina en el ViewPager
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateCityInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateCityInfo();
        return root;
    }
    public void updateCityInfo (){
        // Modificamos el título de la toolbar
        // 1- Accedemos a la actividad que nos contiene
        if (getActivity() instanceof AppCompatActivity){
            AppCompatActivity activy = (AppCompatActivity)getActivity();
            // 2- Acceder dentro de la actividad a la actionbar
            ActionBar actionBar = activy.getSupportActionBar();
            // 3- Cambiar el texto de la toolbar
            actionBar.setTitle(mCities.getCities().get(mViewPager.getCurrentItem()).getName());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.citypager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.previus:
                // Retrocedemos una página
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                updateCityInfo();
                return true;
            case R.id.next:
                // Avanzamos una página
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                updateCityInfo();
                return true;
        }
        return superValue;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(mViewPager != null) {
            MenuItem menuPrev = menu.findItem(R.id.previus);
            MenuItem menuNext = menu.findItem(R.id.next);

            menuNext.setEnabled(mViewPager.getCurrentItem() < mCities.getCities().size() - 1);
            menuPrev.setEnabled(mViewPager.getCurrentItem() > 0);
        }
    }
}

class CityPagerAdapter extends FragmentPagerAdapter {

    private Cities mCities;

    public CityPagerAdapter(FragmentManager fm, Cities cities) {
        super(fm);
        mCities= cities;
    }

    @Override
    public Fragment getItem(int position) {
        // Saco del modelo la información que necesita el fragment
        City city = mCities.getCities().get(position);

        // Instancio el fragment a mostrar en el viewPager
        ForecastFragment fragment = ForecastFragment.newInstance(city);

        // Devuelvo el fragment listo para ser mostrado
        return fragment;
    }

    @Override
    public int getCount() {
        return mCities.getCities().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return  mCities.getCities().get(position).getName();
    }
}
