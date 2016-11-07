package com.benjagarrido.guedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private ImageView forecastImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cargamos la interfaz
        setContentView(R.layout.activity_main);

        // Accedemos a imageView a través de su id
        forecastImage = (ImageView) findViewById(R.id.forecast_image);
        // Asociamos el botón con el controlador
        final ToggleButton btnForecastChange = (ToggleButton) findViewById(R.id.btn_forecast_change);
        btnForecastChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnForecastChange.isChecked()){
                    changeToSpanishSystem(v);
                } else {
                    changeToAmericanSystem(v);
                }
            }
        });
    }
    public void changeToSpanishSystem (View view){
        Log.v(TAG,"Dentro del método changeToSpanishSystem");
        // Cambiamos la imágen a mostrar
        forecastImage.setImageResource(R.drawable.offline_weather2);
    }

    public void changeToAmericanSystem (View view){
        Log.v(TAG,"Dentro del método changeToAmericanSystem");
        // Cambiamos la imágen a mostrar
        forecastImage.setImageResource(R.drawable.offline_weather);
    }
}
