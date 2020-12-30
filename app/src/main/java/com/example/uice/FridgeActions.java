package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.PorterDuff;
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
                ImageButton btn = (ImageButton) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        addWater(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.getBackground().clearColorFilter();
                        break;
                }
                return false;
            }
        });

        add_tap_water = findViewById(R.id.tap_water_button);
        add_tap_water.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton btn = (ImageButton) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        addWater(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.getBackground().clearColorFilter();
                        break;
                }
                return false;
            }
        });

        add_ice = findViewById(R.id.ice_button);
        add_ice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton btn = (ImageButton) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        addIce(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.getBackground().clearColorFilter();
                        break;
                }
                return false;
            }
        });

        add_crushed_ice = findViewById(R.id.crushed_ice_button);
        add_crushed_ice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageButton btn = (ImageButton) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        btn.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        addIce(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn.getBackground().clearColorFilter();
                        break;
                }
                return false;
            }
        });
    }

    public void openMainMenu(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
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