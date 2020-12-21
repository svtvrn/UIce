package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class FridgeActions extends AppCompatActivity {

    private ImageButton back;
    private ImageButton add_water;
    private ImageButton add_ice_1;
    private ImageButton add_ice_2;
    private ImageButton add_ice_3;

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

        add_water = (ImageButton) findViewById(R.id.add_water_button);
        add_water.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                addWater();
                return true;
            }

        });

        add_ice_1 = (ImageButton) findViewById(R.id.add_ice_1_button);
        add_ice_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIce(1);
            }
        });

        add_ice_2 = (ImageButton) findViewById(R.id.add_ice_2_button);
        add_ice_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIce(2);
            }
        });

        add_ice_3 = (ImageButton) findViewById(R.id.add_ice_3_button);
        add_ice_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIce(3);
            }
        });

    }

    public void openMainMenu(){
        super.onBackPressed();
    }

    public void addWater() {
        Toast.makeText(getApplicationContext(), "Adding Water...", Toast.LENGTH_LONG).show();
    }

    public void addIce(int amount) {
        if (amount==1) {
            Toast.makeText(getApplicationContext(), "Added Ice 1.", Toast.LENGTH_SHORT).show();
        } else if (amount==2) {
            Toast.makeText(getApplicationContext(), "Added Ice 2.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Added Ice 3.", Toast.LENGTH_SHORT).show();
        }
    }

}