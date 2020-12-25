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
import android.widget.RadioButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TempAdapter.OnTemperatureListener {

    private static final int REQUEST_CODE_SETTINGS = 1;
    private boolean currentScale;
    private boolean interruptLongClick;
    private String selectedValue;
    private ArrayList<String> values;
    private ArrayList<String> fridge_list = new ArrayList<>();
    private ArrayList<String> freezer_list = new ArrayList<>();

    private SharedPreferences appSettingsPreferences;
    private SharedPreferences.Editor prefEditor;
    private int currentFridgeTemp;
    private int currentFreezerTemp;
    private int adapterPosition;

    private Button fridge;
    private Button freezer;
    private ImageButton actions;
    private ImageButton notes;
    private ImageButton settings;
    private ImageButton powerFreeze;
    private Button save;
    private Button cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TempAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSettingsPreferences = getSharedPreferences("AppSettingsPrefs",0);
        prefEditor = appSettingsPreferences.edit();
        boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode",false);
        boolean currentScale = appSettingsPreferences.getBoolean("TemperatureScale",true);
        currentFridgeTemp = appSettingsPreferences.getInt("FridgeTemp",0);
        currentFreezerTemp = appSettingsPreferences.getInt("FreezerTemp",1);

        // Checks if Dark Theme is enabled.
        if (isNightModeOn) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Calendar calendar = Calendar.getInstance();
        TextView date = findViewById(R.id.date);
        date.setText(DateFormat.getDateInstance().format(calendar.getTime()));

        fridge = (Button) findViewById(R.id.fridge_temp_button);
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(true);
            }
        });

        freezer = (Button) findViewById(R.id.freezer_temp_button);
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

        powerFreeze = (ImageButton) findViewById(R.id.power_freeze_button);
        powerFreeze.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                activatePowerFreeze();
                return true;
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

        setScale(currentScale);
    }

    public void openFridgePopup(final Boolean type) {

        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View tempPopupView = getLayoutInflater().inflate(R.layout.fridge_popup,null);

        RecyclerView temperatures  = (RecyclerView) tempPopupView.findViewById(R.id.temp_recycler_view);
        temperatures.setLayoutManager(new LinearLayoutManager(this));

        if (type) values = fridge_list;
        else values = freezer_list;
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
                    if (type) {
                        currentFridgeTemp = adapterPosition;
                        fridge.setText(values.get(currentFridgeTemp));
                        prefEditor.putInt("FridgeTemp",currentFridgeTemp);
                    }
                    else{
                        currentFreezerTemp = adapterPosition;
                        freezer.setText(values.get(currentFreezerTemp));
                        prefEditor.putInt("FreezerTemp",currentFreezerTemp);
                    }
                    prefEditor.apply();
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
        adapterPosition = position;
    }

    public void openActionsActivity(){
        Intent intent = new Intent (this,FridgeActions.class);
        startActivity(intent);
    }

    public void activatePowerFreeze(){
        for(int i=3; i>0; i--){
            Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "Power freeze activated.", Toast.LENGTH_SHORT).show();
    }

    public void openNotesActivity(){
        Intent intent = new Intent (/*this,FridgeNotes.class*/);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent (this,FridgeSettings.class);
        startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SETTINGS) {
            if (resultCode == RESULT_OK) {
                currentScale =  data.getExtras().getBoolean(FridgeSettings.SCALE);
                if (adapter != null) adapter.notifyDataSetChanged();
                setScale(currentScale);
            }
        }
    }

    // Sets the Temperature Scale
    // True: Celsius, False: Fahrenheit
    private void setScale(boolean scale) {
        if (scale) {
            fridge_list = new ArrayList<String>(Arrays.asList("0°C", "1°C", "2°C", "3°C", "4°C", "5°C", "6°C", "7°C"));
            freezer_list = new ArrayList<String>(Arrays.asList("-23°C", "-22°C", "-21°C", "-20°C", "-19°C", "-18°C", "-17°C", "-16°C", "-15°C", "-14°C"));
        } else {
            fridge_list = new ArrayList<String>(Arrays.asList("32°F", "34°F", "36°F", "37°F", "39°F", "41°F", "43°F", "50°F"));
            freezer_list = new ArrayList<String>(Arrays.asList("-9°F", "-8°F", "-6°F", "-4°F", "-2°F", "0°F", "1°F", "3°F", "5°F", "7°F"));
        }
        if (selectedValue == null) {
            fridge.setText(fridge_list.get(currentFridgeTemp));
            freezer.setText(String.valueOf(freezer_list.get(currentFreezerTemp)));
        }
    }

}