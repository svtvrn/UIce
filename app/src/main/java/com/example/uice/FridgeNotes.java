package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;


public class FridgeNotes extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint brush = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_notes);
    }

    public void pencil(View view){
        brush.setColor(Color.BLACK);
        currentColor(brush.getColor());
    }
    public void eraser(View view){
        brush.setColor(Color.WHITE);
        currentColor(brush.getColor());
    }
    public void blackColour(View view){
        brush.setColor(Color.BLACK);
        currentColor(brush.getColor());
    }
    public void redColour(View view){
        brush.setColor(Color.RED);
        currentColor(brush.getColor());
    }
    public void yellowColour(View view){
        brush.setColor(Color.YELLOW);
        currentColor(brush.getColor());
    }
    public void blueColour(View view){
        brush.setColor(Color.BLUE);
        currentColor(brush.getColor());
    }

    public void currentColor(int c){
        Board.currentBrush = c;
        path = new Path();
    }

    public void clear(View view) {
        Board.pathList.clear();
        Board.colorList.clear();
        path.reset();
    }

    public void back(View view) {
        super.onBackPressed();
    }
}