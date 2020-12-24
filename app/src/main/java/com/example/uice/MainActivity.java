package com.example.uice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TempAdapter.OnTemperatureListener {

    boolean temp_scale = false;
    private String fridge_temp;
    private String freezer_temp;
    private Button fridge;
    private Button freezer;
    private ImageButton actions;
    private ImageButton notes;
    private ImageButton settings;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    TempAdapter adapter;
    String selectedValue;
    private Button save;
    private Button cancel;

    ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checks if Dark Theme is enabled.
        SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingsPrefs",0);
        Boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode",false);
        if (isNightModeOn) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        TextClock clock = (TextClock) findViewById(R.id.clock);
        Calendar calendar = Calendar.getInstance();
        TextView date = findViewById(R.id.date);
        date.setText(DateFormat.getDateInstance().format(calendar.getTime()));

        fridge = (Button) findViewById(R.id.fridge_temp_button);
        if (temp_scale) fridge_temp = "6°C";
        else fridge_temp = "43°F";
        fridge.setText(String.valueOf(fridge_temp));
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(true);
            }
        });

        freezer = (Button) findViewById(R.id.freezer_temp_button);
        if (temp_scale) freezer_temp = "-19°C";
        else freezer_temp = "-2°F";
        freezer.setText(String.valueOf(freezer_temp));
        freezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(false);
            }
        });

        actions = (ImageButton) findViewById(R.id.ice_water_button);
        actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActionsActivity();
            }
        });

        notes = (ImageButton) findViewById(R.id.notes_button);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotesActivity();
            }
        });

        settings = (ImageButton) findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

    }

    public void openActionsActivity(){
        Intent intent = new Intent (this,FridgeActions.class);
        startActivity(intent);
    }

    public void openNotesActivity(){
        Intent intent = new Intent (/*this,FridgeNotes.class*/);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent (this,FridgeSettings.class);
        startActivity(intent);
    }

    public void openFridgePopup(final Boolean type) {

        values.clear();
        if (type) {
            if (temp_scale) values = new ArrayList<String>(Arrays.asList("0°C", "1°C", "2°C", "3°C", "4°C", "5°C", "6°C", "7°C"));
            else values = new ArrayList<String>(Arrays.asList("32°F", "34°F", "36°F", "37°F", "39°F", "41°F", "43°F", "50°F"));
        } else {
            if (temp_scale) values = new ArrayList<String>(Arrays.asList("-23°C", "-22°C", "-21°C", "-20°C", "-19°C", "-18°C", "-17°C", "-16°C", "-15°C", "-14°C"));
            else values = new ArrayList<String>(Arrays.asList("-9°F", "-8°F", "-6°F", "-4°F", "-2°F", "0°F", "1°F", "3°F", "5°F", "7°F"));
        }

        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View tempPopupView = getLayoutInflater().inflate(R.layout.fridge_popup,null);

        RecyclerView temperatures  = (RecyclerView) tempPopupView.findViewById(R.id.temp_recycler_view);
        temperatures.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TempAdapter(this, values,this);
        temperatures.setAdapter(adapter);

        save = (Button) tempPopupView.findViewById(R.id.save_temp_button);
        cancel = (Button) tempPopupView.findViewById(R.id.cancel_temp_button);
        dialogBuilder.setView(tempPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedValue!=null) {
                    if (type) fridge.setText(selectedValue);
                    else freezer.setText(selectedValue);
                    dialog.dismiss();
                    selectedValue = null;
                } else Toast.makeText(getApplicationContext(), "Please select a temperature.", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onTempClick(int position) {
        selectedValue = values.get(position);
    }
    
}