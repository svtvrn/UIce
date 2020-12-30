package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FridgeNotes extends AppCompatActivity {

    public static Path path = new Path();
    public static Paint brush = new Paint();
    private ImageButton pen;
    private ImageButton eraser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_notes);

        pen = findViewById(R.id.pencil);
        pen.getBackground().setColorFilter(Color.parseColor("#5a0974"), PorterDuff.Mode.MULTIPLY);
        eraser = findViewById(R.id.eraser);
    }

    public void pencil(View view){
        pen.getBackground().setColorFilter(Color.parseColor("#5a0974"), PorterDuff.Mode.MULTIPLY);
        eraser.getBackground().clearColorFilter();
        brush.setColor(Color.BLACK);
        currentColor(brush.getColor());
    }
    public void eraser(View view){
        eraser.getBackground().setColorFilter(Color.parseColor("#5a0974"), PorterDuff.Mode.MULTIPLY);
        pen.getBackground().clearColorFilter();
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
       onBackPressed();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}