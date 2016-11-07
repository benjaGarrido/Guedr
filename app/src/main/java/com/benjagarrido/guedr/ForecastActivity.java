package com.benjagarrido.guedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ForecastActivity extends AppCompatActivity {
    private static final String TAG = ForecastActivity.class.getName();
    private TextView mMaxTemp;
    private TextView mMinTemp;
    private TextView mHumidity;
    private TextView mDescription;
    private ImageView mForecastImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargamos la interfaz
        setContentView(R.layout.activity_forecast);

        mMaxTemp = (TextView)findViewById(R.id.txtMaxTemp);
        mMinTemp = (TextView)findViewById(R.id.txtMinTemp);
        mHumidity = (TextView)findViewById(R.id.txtHumidity);
        mDescription = (TextView)findViewById(R.id.txtForecast);
        mForecastImage = (ImageView)findViewById(R.id.imgforecast);

        // Creo mi modelo
        Forecast forecast = new Forecast(30,15,25,"Algunas nubes",R.drawable.sun_cloud);
        setForecast(forecast);

    }
    public void setForecast (Forecast forecast){
        // Muestro en la interfaz mi modelo
        mMaxTemp.setText(String.format(getString(R.string.temperatura_maxima),forecast.getMaxTemp()));
        mMinTemp.setText(String.format(getString(R.string.temperatura_minima),forecast.getMimTemp()));
        mHumidity.setText(String.format(getString(R.string.humedad),forecast.getHumidity()));
        mDescription.setText(forecast.getDescription());
        mForecastImage.setImageResource(forecast.getIcon());
    }
}
