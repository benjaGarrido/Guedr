package com.benjagarrido.guedr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ForecastActivity extends AppCompatActivity {
    private static final String sTAG = ForecastActivity.class.getName();
    private static final int sREQUEST_UNITS=1;
    private static final String sPREFERENCE_UNITS = "units";

    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;
    private boolean showCelsius;
    private Forecast mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargamos la interfaz
        setContentView(R.layout.activity_forecast);

        // Cargamos los valores de la última vez (guardados en SharedPreferences)
        showCelsius= PreferenceManager.getDefaultSharedPreferences(this).getBoolean(sPREFERENCE_UNITS,true);

        // Asociamos vista con controlador
        mMaxTemp = (TextView)findViewById(R.id.txtMaxTemp);
        mMinTemp = (TextView)findViewById(R.id.txtMinTemp);
        mHumidity = (TextView)findViewById(R.id.txtHumidity);
        mDescription = (TextView)findViewById(R.id.txtForecast);
        mForecastImage = (ImageView)findViewById(R.id.imgforecast);

        // Creo mi modelo
        mForecast = new Forecast(30,15,25,"Algunas nubes",R.drawable.sun_cloud);
        setForecast(mForecast);

    }

    // Este establece cómo es el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_forecast, menu);
        return true;
    }

    // Este indica qué pasa cuando pulsamos una opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.men_show_settings:
                // Lanzamos la actividad Settings_Activity
                Intent intent = new Intent(this,SettingsActivity.class);
                // Le pasamos los datos a la pantalla de ajustes
                intent.putExtra(SettingsActivity.EXTRA_CURRENT_UNITS,showCelsius);
                // Iniciamos el intent explícito indicándole la respuesta que esperamos
                startActivityForResult(intent, sREQUEST_UNITS);
                return true;
        }
        return superReturn;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case sREQUEST_UNITS:
                seleccionarSettings(resultCode, data);
        }
    }

    private void seleccionarSettings(int resultCode, Intent data) {
        Log.v(sTAG,"Entrando en seleccionarSettings");
        // Estoy manejando el resultado de la pantalla de ajustes
        if (resultCode == RESULT_OK){
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

            /*
            // Persistimos las preferencias
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            // Entramos en modo edición de preferencias
            SharedPreferences.Editor editor = prefs.edit();
            // Guardamos los datos
            editor.putBoolean(sPREFERENCE_UNITS, showCelsius);
            // Si no hacemos commit no guarda nada
            editor.commit();
            */

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putBoolean(sPREFERENCE_UNITS,showCelsius)
                    .commit();

            // Actualizamos la interfaz con las nuevas unidades
            setForecast(mForecast);

            // Avisamos al usuario de que los ajustes han cambiado
            // Toast.makeText(this,"Preferencias actualizadas",Toast.LENGTH_LONG).show();
            Snackbar.make(findViewById(android.R.id.content), R.string.preferencias_actualizadas,Snackbar.LENGTH_LONG)
                    .setAction(R.string.deshacer, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Restauramos el valor
                            showCelsius = oldShowCelsius;
                            // Guardamos el valor en las preferencias
                            PreferenceManager.getDefaultSharedPreferences(ForecastActivity.this)
                                    .edit()
                                    .putBoolean(sPREFERENCE_UNITS,showCelsius)
                                    .commit();
                            // Actualizamos el modelo
                            setForecast(mForecast);
                        }
                    })
                    .show();
        }
    }

    public void setForecast (Forecast forecast){
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
