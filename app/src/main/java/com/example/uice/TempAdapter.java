package com.example.uice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder> {
    private List<TextView> mData;
    private LayoutInflater mInflater;
    private OnTemperatureListener onTemperatureListener;

    int lastClicked = -1;

    public interface OnTemperatureListener {
        void onTempClick(int position);
    }

    // data is passed into the constructor
    TempAdapter(Context context, List<TextView> data,OnTemperatureListener onTemperatureListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this. onTemperatureListener = onTemperatureListener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.temp_item, parent, false);
        return new ViewHolder(view,onTemperatureListener);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView temperature = mData.get(position);
        holder.myTextView.setText(temperature.getText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTextView;
        OnTemperatureListener onTemperatureListener;

        ViewHolder(View itemView, OnTemperatureListener onTemperatureListener) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.temp_text);
            this.onTemperatureListener = onTemperatureListener;

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {
            if(lastClicked!=-1) {
                mData.get(lastClicked).setTextColor(Color.BLACK);
                notifyItemChanged(lastClicked);
            }
            lastClicked = getAdapterPosition();
            myTextView.setTextColor(Color.RED);
            onTemperatureListener.onTempClick(lastClicked);

        }
    }

}