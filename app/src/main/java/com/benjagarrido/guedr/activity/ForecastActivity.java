package com.benjagarrido.guedr.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.fragment.CityListFragment;
import com.benjagarrido.guedr.fragment.CityPagerFragment;
import com.benjagarrido.guedr.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.CityListListener {
    private static final String TAG = ForecastActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Si necesitamos conocer ciertas características de algunos de los dispositivos que usamos (medidas reales)
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int dpWidth = (int)(width/metrics.density);
        int dpHeight = (int)(height/metrics.density);
        String model = Build.MODEL;
        int dpi = metrics.densityDpi;

        // Le indicamos a la actividad la toolbar a utilizar @layout/toolbar_main
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMainToolbar);

        // Le decimos a la actividad que queremos mostrar esa vista toolbar como nuestra toolbar
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();

        // Para evitar añadir varios fragment comprobamos que no está añadido a nuestra jerarquía
        // Recuerda que las actividades de android se vuelven a crear en varias situaciones:
        // - Cuando sale el teclado
        // - Cuando cambia el tamaño de la aplicación por algún motivo
        // - Cuando existe una falta de memoria
        // - Cuando se gira el dispositivo
        // Preguntamos a ver si hemos cargado la interfaz donde tenemos un hueco para el City List
        if(fm.findFragmentById(R.id.fragment_city_list) != null) {
            // Si hay hueco, metemos el fragment
            if (fm.findFragmentById(R.id.fragment_city_list) == null) {
                // Como no existe lo añadimos con una transacción a nuestra jerarquía de vistas
                fm.beginTransaction()
                        .add(R.id.fragment_city_list, new CityListFragment())
                        .commit();
            }
        }

        // Preguntamos a ver si hemos cargado la interfaz donde tenemos un hueco para el City Pager
        if (findViewById(R.id.fragment_city_pager) != null){
            // Si hay hueco, metemos el fragment
            if(fm.findFragmentById(R.id.fragment_city_pager) == null){
                // Como no existe lo añadimos con una transacción a nuestra jerarquía de vistas
                fm.beginTransaction()
                        .add(R.id.fragment_city_pager, CityPagerFragment.newInstance(0))
                        .commit();
            }
        }
    }

    @Override
    public void onCitySelected(City city, int position) {
        // Aquí me entero de que una ciudad ha sido seleccionada en el CityListFragment
        // Tendré que mostrar la ciudad en el CityPagerFragment
        Log.v(TAG, "Se ha seleccionado la ciudad número " + position);

        // Vamos a comprobar si tenemos un pager en nuestra interfaz
        FragmentManager fm = getFragmentManager();
        CityPagerFragment cityPagerFragment = (CityPagerFragment) fm.findFragmentById(R.id.fragment_city_pager);

        // Si cityPagerFragment no es null es que tenemos referencia a él
        if (cityPagerFragment != null){
            // Le diremos al fragment que muestre la ciudad de la posicion "position"
            cityPagerFragment.showCity(position);
        } else {
            // No tenemos pager, arrancamos otra actividad
            Intent intent = new Intent(this, CityPagerActivity.class);
            intent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX,position);

            startActivity(intent);
        }
    }
}
