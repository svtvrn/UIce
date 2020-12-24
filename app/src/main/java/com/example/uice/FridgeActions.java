package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class FridgeActions extends AppCompatActivity {

    private ImageButton back;
    private ImageButton add_water;
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

        add_water = (ImageButton) findViewById(R.id.add_water_button);
        add_water.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                addWater();
                return true;
            }

        });

        add_ice = (ImageButton) findViewById(R.id.add_ice_button);
        add_ice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIce(false);
            }
        });

        add_crushed_ice = (ImageButton) findViewById(R.id.add_crushed_ice_button);
        add_crushed_ice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIce(true);
            }
        });
    }

    public void openMainMenu(){
        super.onBackPressed();
    }

    public void addWater() {
        Toast.makeText(getApplicationContext(), "Adding Water...", Toast.LENGTH_LONG).show();
    }

    public void addIce(boolean type) {
        if (!type) {
            Toast.makeText(getApplicationContext(), "Added ice.", Toast.LENGTH_SHORT).show();
        } else if (type) {
            Toast.makeText(getApplicationContext(), "Added crushed ice.", Toast.LENGTH_SHORT).show();
        }
    }

}