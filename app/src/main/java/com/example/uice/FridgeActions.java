package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class FridgeActions extends AppCompatActivity {

    private ImageButton back;
    private ImageButton add_tap_water;
    private ImageButton add_cold_water;
    private ImageButton add_ice;
    private ImageButton add_crushed_ice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_actions);

        back = findViewById(R.id.fridge_actions_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainMenu();
            }
        });

        add_cold_water = findViewById(R.id.cold_water_button);
        add_cold_water.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addWater(true);
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        add_tap_water = findViewById(R.id.tap_water_button);
        add_tap_water.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addWater(false);
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        add_ice = findViewById(R.id.ice_button);
        add_ice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addIce(false);
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        add_crushed_ice = findViewById(R.id.crushed_ice_button);
        add_crushed_ice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        addIce(true);
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    public void openMainMenu(){
        super.onBackPressed();
    }

    public void addWater(boolean type) {
        if (!type) {
            Toast.makeText(getApplicationContext(), "Adding tap water...", Toast.LENGTH_SHORT).show();
        }else if(type){
            Toast.makeText(getApplicationContext(), "Adding cold water...", Toast.LENGTH_SHORT).show();
        }
    }

    public void addIce(boolean type) {
        if (!type) {
            Toast.makeText(getApplicationContext(), "Adding ice...", Toast.LENGTH_SHORT).show();
        } else if (type) {
            Toast.makeText(getApplicationContext(), "Adding crushed ice...", Toast.LENGTH_SHORT).show();
        }
    }

}