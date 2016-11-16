package com.benjagarrido.guedr.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.fragment.CityPagerFragment;

/**
 * Created by benjamingarridobarreiro on 16/11/16.
 */

public class CityPagerActivity extends AppCompatActivity {
    private static final String TAG = CityPagerActivity.class.getName();
    public final static String EXTRA_CITY_INDEX = TAG + ".EXTRA_CITY_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        // Le indicamos a la actividad la toolbar a utilizar @layout/toolbar_main
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMainToolbar);

        // Le decimos a la actividad que queremos mostrar esa vista toolbar como nuestra toolbar
        setSupportActionBar(toolbar);
        // Añadimos la flecha de back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        FragmentManager fm = getFragmentManager();

        // Para evitar añadir varios fragment comprobamos que no está añadido a nuestra jerarquía
        // Recuerda que las actividades de android se vuelven a crear en varias situaciones:
        // - Cuando sale el teclado
        // - Cuando cambia el tamaño de la aplicación por algún motivo
        // - Cuando existe una falta de memoria
        // - Cuando se gira el dispositivo
        if(fm.findFragmentById(R.id.flCityPager) == null){
            int initialCityIndex = getIntent().getIntExtra(EXTRA_CITY_INDEX,0);

            // Como no existe lo añadimos con una transacción a nuestra jerarquía de vistas
            fm.beginTransaction()
                    .add(R.id.flCityPager, new CityPagerFragment().newInstance(initialCityIndex))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home){
            // Han pulsado la flecha de back en la barra y por lo tanto debemos finalizar la actividad
            finish();
            return true;
        }
        return superValue;
    }
}
