package com.example.uice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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
        Board.pathList.clear();
        Board.colorList.clear();
        path.reset();
    }
    public void whiteColour(View view){
        brush.setColor(Color.WHITE);
        currentColor(brush.getColor());
    }
    public void blackColour(View view){
        brush.setColor(Color.WHITE);
        currentColor(brush.getColor());
    }
    public void redColour(View view){
        brush.setColor(Color.BLACK);
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


     static class Board extends View {

        static ArrayList<Path> pathList = new ArrayList<>();
        static ArrayList<Integer> colorList = new ArrayList<>();
        static ViewGroup.LayoutParams params;
        static int currentBrush = R.id.black_colour;

        public Board(Context context) {
            super(context);
            init(context);
        }

        void init (Context context){
            brush.setAntiAlias(true);
            brush.setColor(currentBrush);
            brush.setStyle(Paint.Style.STROKE);
            brush.setStrokeCap(Paint.Cap.ROUND);
            brush.setStrokeJoin(Paint.Join.ROUND);
            brush.setStrokeWidth(10f);

            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x,y);
                    invalidate();
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x,y);
                    pathList.add(path);
                    colorList.add(currentBrush);
                    invalidate();
                    return true;
                default:
                    return false;
            }
        }
        @Override
        public void onDraw(Canvas canvas){
            for(int i=0; i<pathList.size(); i++){
                brush.setColor(colorList.get(i));
                canvas.drawPath(pathList.get(i),brush);
                invalidate();
            }
        }
    }
}