package com.example.uice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TempAdapter.OnTemperatureListener {

    private static final int REQUEST_CODE_SETTINGS = 1;
    private boolean currentScale;
    private String selectedValue;
    private ArrayList<String> values;
    private ArrayList<String> fridge_list = new ArrayList<>();
    private ArrayList<String> freezer_list = new ArrayList<>();
    private SharedPreferences appSettingsPreferences;
    private SharedPreferences.Editor prefEditor;
    private int currentFridgeTemp;
    private int currentFreezerTemp;
    public static int adapterPosition;
    private int humidityControl;
    private Button fridge;
    private Button freezer;
    private ImageButton actions;
    private ImageButton notes;
    private ImageButton settings;
    private ImageButton powerFreeze;
    private Button save;
    private Button cancel;
    private AlertDialog.Builder dialogBuilder;
    private SeekBar humidityControlBar;
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

        final Handler handler = new Handler();

        fridge =  findViewById(R.id.fridge_temp_button);
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(true);
            }
        });

        freezer =  findViewById(R.id.freezer_temp_button);
        freezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(false);
            }
        });

        actions = findViewById(R.id.ice_water_button);
        actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActionsActivity();
            }
        });

        powerFreeze = findViewById(R.id.power_freeze_button);
        powerFreeze.setOnTouchListener(new View.OnTouchListener() {
            Runnable activatePowerFreeze = new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Power freeze activated.", Toast.LENGTH_SHORT).show();
                }
            };
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton btn = (ImageButton) v;
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    btn.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    handler.postDelayed(activatePowerFreeze,3000);
                    break;
                case MotionEvent.ACTION_UP:
                    btn.getBackground().clearColorFilter();
                    handler.removeCallbacks(activatePowerFreeze);
                    break;
                }
                return false;
            }
        });

        notes =  findViewById(R.id.notes_button);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotesActivity();
            }
        });

        settings = findViewById(R.id.settings_button);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        humidityControlBar = findViewById(R.id.humidity_control_bar);
        humidityControlBar.setMax(5);

        humidityControl = appSettingsPreferences.getInt("HumidityControl",3);
        if(humidityControl!=3) humidityControlBar.setProgress(humidityControl);
        humidityControlBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                humidityControl = progress;
                prefEditor.putInt("HumidityControl",humidityControl);
                prefEditor.apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        setScale(currentScale);
    }

    public void openFridgePopup(final Boolean type) {

        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View tempPopupView = getLayoutInflater().inflate(R.layout.fridge_popup,null);

        RecyclerView temperatures  =  tempPopupView.findViewById(R.id.temp_recycler_view);
        temperatures.setLayoutManager(new LinearLayoutManager(this));

        if (type) values = fridge_list;
        else values = freezer_list;
        adapter = new TempAdapter(this, values,this);
        temperatures.setAdapter(adapter);

        save =  tempPopupView.findViewById(R.id.save_temp_button);
        cancel =  tempPopupView.findViewById(R.id.cancel_temp_button);
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
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public void openNotesActivity(){
        Intent intent = new Intent (this,FridgeNotes.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent (this,FridgeSettings.class);
        startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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