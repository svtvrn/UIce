package com.example.uice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class FridgeSettings extends AppCompatActivity {

    public static final String SCALE = "scale";

    private ImageButton back;
    private SwitchCompat nightSwitch;
    private SeekBar brightnessBar;
    private int sysBrightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_settings);

        back = findViewById(R.id.fridge_actions_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        askPermission(this);
        brightnessBar = findViewById(R.id.brightness_bar);
        brightnessBar.setMax(255);

        sysBrightness =android.provider.Settings.System.getInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,0);
        brightnessBar.setProgress(sysBrightness);

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS,progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingsPrefs",0);
        final SharedPreferences.Editor prefEditor = appSettingsPreferences.edit();
        nightSwitch = (SwitchCompat) findViewById(R.id.night_mode_switch);
        Boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode",false);
        if (isNightModeOn) nightSwitch.setChecked(true);
        else nightSwitch.setChecked(false);
        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    prefEditor.putBoolean("NightMode",true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    prefEditor.putBoolean("NightMode",false);
                }
                prefEditor.apply();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(SCALE, false);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    public void askPermission(Context context) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(context)) { }
            else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                context.startActivity(intent);
            }
        }
    }
}