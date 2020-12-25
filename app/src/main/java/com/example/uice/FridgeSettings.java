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
    private boolean currentScale;

    private ImageButton back;
    private SwitchCompat nightSwitch;
    private SeekBar screenBrightnessBar;
    private SeekBar fridgeBrightnessBar;
    private RadioGroup scaleRadioGroup;

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
        if(fridgeBrightness!=105){
            fridgeBrightnessBar.setProgress(fridgeBrightness);
        }
        fridgeBrightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fridgeBrightness = progress;
                prefEditor.putInt("FridgeBrightness",fridgeBrightness);
                prefEditor.apply();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

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
        boolean isNightModeOn = appSettingsPreferences.getBoolean("NightMode",false);
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

        scaleRadioGroup = findViewById(R.id.scale_radiogroup);
        currentScale = appSettingsPreferences.getBoolean("TemperatureScale",true);
        if (currentScale) ((RadioButton)scaleRadioGroup.getChildAt(0)).setChecked(true);
        else ((RadioButton)scaleRadioGroup.getChildAt(1)).setChecked(true);
        scaleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == findViewById(R.id.celsius).getId()){
                    currentScale=true;
                    prefEditor.putBoolean("TemperatureScale",currentScale);
                }else if(checkedId == findViewById(R.id.fahrenheit).getId()){
                    currentScale=false;
                    prefEditor.putBoolean("TemperatureScale",currentScale);
                }
                prefEditor.apply();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(SCALE, currentScale);
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