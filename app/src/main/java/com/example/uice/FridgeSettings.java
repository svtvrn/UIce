package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class FridgeSettings extends AppCompatActivity {

    private ImageButton back;
    private SwitchCompat nightSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_settings);

        back = findViewById(R.id.fridge_actions_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingsPrefs",0);
        final SharedPreferences.Editor prefEditor = appSettingsPreferences.edit();
        Boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode",false);
        nightSwitch = (SwitchCompat) findViewById(R.id.night_mode_switch);

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            nightSwitch.setChecked(true);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            nightSwitch.setChecked(false);
        }



        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    prefEditor.putBoolean("NightMode",true);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    prefEditor.putBoolean("NightMode",false);
                }
                prefEditor.apply();
            }
        });
    }

    public void openMainMenu(){
        super.onBackPressed();
    }
}