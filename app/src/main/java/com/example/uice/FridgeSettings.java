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
    private int sysBrightness;
    private int fridgeBrightness;
    private boolean temp_scale = true;

    private ImageButton back;
    private SwitchCompat nightSwitch;
    private SeekBar screenBrightnessBar;
    private SeekBar fridgeBrightnessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_settings);
        SharedPreferences appSettingsPreferences = getSharedPreferences("AppSettingsPrefs",0);
        final SharedPreferences.Editor prefEditor = appSettingsPreferences.edit();
        askPermission(this);

        back = findViewById(R.id.fridge_actions_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fridgeBrightnessBar = findViewById(R.id.fridge_brightness_bar);
        fridgeBrightnessBar.setMax(255);

        fridgeBrightness = appSettingsPreferences.getInt("FridgeBrightness",105);

        screenBrightnessBar = findViewById(R.id.brightness_bar);
        screenBrightnessBar.setMax(255);

        sysBrightness =android.provider.Settings.System.getInt(getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS,0);
        screenBrightnessBar.setProgress(sysBrightness);

        screenBrightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        intent.putExtra(SCALE, temp_scale);
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

//TODO Fridge Brightness Bar
//TODO Radio Buttons