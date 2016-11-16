package com.benjagarrido.guedr.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.fragment.CityListFragment;
import com.benjagarrido.guedr.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.CityListListener {
    private static final String TAG = ForecastActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

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
        if(fm.findFragmentById(R.id.flContent) == null){
            // Como no existe lo añadimos con una transacción a nuestra jerarquía de vistas
            fm.beginTransaction()
                    .add(R.id.flContent, new CityListFragment())
                    .commit();
        }
    }

    @Override
    public void onCitySelected(City city, int position) {
        // Aquí me entero de que una ciudad ha sido seleccionada en el CityListFragment
        // Tendré que mostrar la ciudad en el CityPagerFragment
        Log.v(TAG, "Se ha seleccionado la ciudad número " + position);

        Intent intent = new Intent(this, CityPagerActivity.class);
        intent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX,position);

        startActivity(intent);
    }
}
