package com.example.uice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TempAdapter.OnTemperatureListener {

    private String fridge_temp = "6°C";
    private String freezer_temp = "-19°C";
    private Button fridge;
    private Button freezer;
    private ImageButton actions;
    private ImageButton notes;
    private ImageButton settings;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    TempAdapter adapter;
    String selectedValue;
    private Button save;
    private Button cancel;

    ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextClock clock = (TextClock) findViewById(R.id.clock);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.date);
        textViewDate.setText(currentDate);

        fridge = (Button) findViewById(R.id.fridge_temp_button);
        fridge.setText(String.valueOf(fridge_temp));
        freezer = (Button) findViewById(R.id.freezer_temp_button);
        freezer.setText(String.valueOf(freezer_temp));

        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridgePopup(true);
            }
        });

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
    }

    public void openActionsActivity(){
        Intent intent = new Intent (this,FridgeActions.class);
        startActivity(intent);
    }

    public void openNotesActivity(){
        Intent intent = new Intent (/*this,FridgeNotes.class*/);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent (this,FridgeSettings.class);
        startActivity(intent);
    }

    public void openFridgePopup(final Boolean type){
        values.clear();
        if(type){
            int val = 0;
            for(int i=0; i<8; i++){
                values.add(val + "°C");
                val++;
            }
        }else{
            int val = -23;
            for(int i=0; i<10; i++){
                values.add(val + "°C");
                val++;
            }
        }
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View tempPopupView = getLayoutInflater().inflate(R.layout.fridge_popup,null);

        RecyclerView temperatures  = (RecyclerView) tempPopupView.findViewById(R.id.temp_recycler_view);
        temperatures.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TempAdapter(this, values,this);
        //adapter.setClickListener(new ItemClickListener());
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
                        fridge.setText(selectedValue);

                    }else {
                        freezer.setText(selectedValue);
                        //dialog.dismiss();
                    }
                    dialog.dismiss();
                    selectedValue = null;
                }
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
    }
}