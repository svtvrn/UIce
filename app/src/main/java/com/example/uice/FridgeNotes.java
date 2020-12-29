package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class FridgeNotes extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint brush = new Paint();
    private ImageButton pen;
    private ImageButton eraser;
    private ImageButton clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_notes);

        pen = findViewById(R.id.pencil);
        pen.setColorFilter(Color.parseColor("#5a0974"));
        eraser = findViewById(R.id.eraser);
        clear = findViewById(R.id.clear_notes);
    }

    public void pencil(View view){
        pen.setColorFilter(Color.parseColor("#5a0974"));
        eraser.setColorFilter(null);
        brush.setColor(Color.BLACK);
        currentColor(brush.getColor());
    }
    public void eraser(View view){
        eraser.setColorFilter(Color.parseColor("#5a0974"));
        pen.setColorFilter(null);
        brush.setColor(Color.parseColor("#FFF3F2"));
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
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}