package com.benjagarrido.guedr.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.benjagarrido.guedr.R;

public class SettingsActivity extends AppCompatActivity {
    private static final String sTAG = SettingsActivity.class.getName();
    public static final String EXTRA_CURRENT_UNITS = "current_units";

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Accedo a las vistas
        mRadioGroup = (RadioGroup) findViewById(R.id.rgUnits);

        // Configuramos las acciones de los botones
        findViewById(R.id.btnAcept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptSettings();
            }
        });
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSettings();
            }
        });

        // Indicamos que radiobutton debe estar seleccionado inicialmente
        boolean showCelsius = getIntent().getBooleanExtra(EXTRA_CURRENT_UNITS,true);
        if (showCelsius){
            RadioButton radioCelsius = (RadioButton) findViewById(R.id.rbCelsius);
            radioCelsius.setChecked(true);
        } else{
            RadioButton radioFarenheit = (RadioButton) findViewById(R.id.rbFarenheit);
            radioFarenheit.setChecked(true);
        }

        // Le indicamos a la actividad la toolbar a utilizar @layout/toolbar_main
        Toolbar toolbar = (Toolbar) findViewById(R.id.tbMainToolbar);

        // Le decimos a la actividad que queremos mostrar esa vista toolbar como nuestra toolbar
        setSupportActionBar(toolbar);
    }
    private void cancelSettings() {
        setResult(RESULT_CANCELED);
        // Finalizamos nuestra actividad
        finish();
    }
    private void acceptSettings() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("units",mRadioGroup.getCheckedRadioButtonId());
        setResult(RESULT_OK, returnIntent);
        // Finalizamos nuestra actividad
        finish();
    }
}
