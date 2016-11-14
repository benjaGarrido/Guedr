package com.benjagarrido.guedr.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benjagarrido.guedr.R;
import com.benjagarrido.guedr.activity.ForecastActivity;
import com.benjagarrido.guedr.activity.SettingsActivity;
import com.benjagarrido.guedr.model.City;
import com.benjagarrido.guedr.model.Forecast;

/**
 * Created by benjamingarridobarreiro on 9/11/16.
 */

public class ForecastFragment extends Fragment{
    public static final String ARG_CITY = "city";

    private static final String sTAG = ForecastActivity.class.getName();
    private static final int sREQUEST_UNITS=1;
    private static final String sPREFERENCE_UNITS = "units";

    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;
    private boolean showCelsius;
    private City mCity;

    public static ForecastFragment newInstance(City city){
        ForecastFragment fragment = new ForecastFragment();

        // Le paso al fragment los argumentos que necesita
        Bundle arguments = new Bundle();
        arguments.putSerializable(ForecastFragment.ARG_CITY,city);
        // Asigno los argumentos al fragment
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicamos a la actividad que este fragment tiene menús
        setHasOptionsMenu(true);

        if (getArguments()!=null){
            mCity = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_forecast, container, false);
        // Cargamos los valores de la última vez (guardados en SharedPreferences)
        showCelsius= PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(sPREFERENCE_UNITS,true);

        // Asociamos vista con controlador
        mMaxTemp = (TextView)root.findViewById(R.id.txtMaxTemp);
        mMinTemp = (TextView)root.findViewById(R.id.txtMinTemp);
        mHumidity = (TextView)root.findViewById(R.id.txtHumidity);
        mDescription = (TextView)root.findViewById(R.id.txtForecast);
        mForecastImage = (ImageView)root.findViewById(R.id.imgForecast);

        updateCityInfo();
        return root;
    }

    // Este establece cómo es el menú
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.forecast, menu);
    }

    // Este indica qué pasa cuando pulsamos una opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.men_show_settings:
                // Lanzamos la actividad Settings_Activity
                Intent intent = new Intent(getActivity(),SettingsActivity.class);
                // Le pasamos los datos a la pantalla de ajustes
                intent.putExtra(SettingsActivity.EXTRA_CURRENT_UNITS,showCelsius);
                // Iniciamos el intent explícito indicándole la respuesta que esperamos
                startActivityForResult(intent, sREQUEST_UNITS);
                return true;
        }
        return superReturn;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case sREQUEST_UNITS:
                seleccionarSettings(resultCode, data);
        }
    }

    private void seleccionarSettings(int resultCode, Intent data) {
        Log.v(sTAG,"Entrando en seleccionarSettings");
        // Estoy manejando el resultado de la pantalla de ajustes
        if (resultCode == Activity.RESULT_OK){
            // Guardamos el valor previo de showCelsius por si el usuario quiere deshacer
            final boolean oldShowCelsius = showCelsius;
            // El usuario ha confirmado una modificación
            int optionSelected = data.getIntExtra("units", R.id.rbCelsius);
            if (optionSelected == R.id.rbCelsius){
                // Ha seleccionado unidades Celsius
                Log.v(sTAG,"El usuario ha seleccionado Celsius");
                showCelsius=true;
            } else if (optionSelected == R.id.rbFarenheit){
                // Ha seleccionado unidades Farenheit
                Log.v(sTAG,"El usuario ha seleccionado Farenheit");
                showCelsius=false;
            }

            PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .edit()
                    .putBoolean(sPREFERENCE_UNITS,showCelsius)
                    .apply();

            // Actualizamos la interfaz con las nuevas unidades
            updateCityInfo();

            // Avisamos al usuario de que los ajustes han cambiado
            Snackbar.make(getView(), R.string.preferencias_actualizadas,Snackbar.LENGTH_LONG)
                    .setAction(R.string.deshacer, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Restauramos el valor
                            showCelsius = oldShowCelsius;
                            // Guardamos el valor en las preferencias
                            PreferenceManager.getDefaultSharedPreferences(getActivity())
                                    .edit()
                                    .putBoolean(sPREFERENCE_UNITS,showCelsius)
                                    .apply();
                            // Actualizamos el modelo
                            updateCityInfo();
                        }
                    })
                    .show();
        }
    }

    public void updateCityInfo (){
        Forecast forecast = mCity.getForecast();

        float maxTemp = forecast.getMaxTemp();
        float minTemp=forecast.getMimTemp();

        if (!showCelsius){
            maxTemp = toFarenheit(maxTemp);
            minTemp = toFarenheit(minTemp);
        }

        // Muestro en la interfaz mi modelo
        mMaxTemp.setText(String.format(getString(R.string.temperatura_maxima),maxTemp));
        mMinTemp.setText(String.format(getString(R.string.temperatura_minima),minTemp));
        mHumidity.setText(String.format(getString(R.string.humedad),forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
        mForecastImage.setImageResource(forecast.getIcon());
    }

    protected static float toFarenheit (float celsius){
        return (celsius * 1.8f) + 32;
    }
}
