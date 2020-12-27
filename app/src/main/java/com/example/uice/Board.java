package com.example.uice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import static com.example.uice.FridgeNotes.brush;
import static com.example.uice.FridgeNotes.path;

public class Board extends View {

    static ArrayList<Path> pathList = new ArrayList<>();
    static ArrayList<Integer> colorList = new ArrayList<>();
    ViewGroup.LayoutParams params;
    static int currentBrush = Color.BLACK;

    public Board(Context context) {
        super(context);
        init(context);
    }

    public Board(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public Board(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
        init(context);
    }

    void init (Context context){
        brush.setAntiAlias(true);
        brush.setColor(currentBrush);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeCap(Paint.Cap.ROUND);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(20f);

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