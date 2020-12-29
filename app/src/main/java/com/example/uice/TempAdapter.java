package com.example.uice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private OnTemperatureListener onTemperatureListener;

    TextView lastTextview = null;

    public interface OnTemperatureListener {
        void onTempClick(int position);
    }

    // data is passed into the constructor
    TempAdapter(Context context, List<String> data,OnTemperatureListener onTemperatureListener) {
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
        String temperature = mData.get(position);
        holder.myTextView.setText(temperature);
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
            if(lastTextview != null) {
                lastTextview.setTextColor(ContextCompat.getColor(view.getContext(), R.color.textPrimary));
                lastTextview.setBackgroundResource(R.color.backgroundPrimary);
            }
            myTextView.setBackgroundResource(R.drawable.roundbutton);
            myTextView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.selectedPrimary));

            lastTextview = myTextView;
            onTemperatureListener.onTempClick(getAdapterPosition());
        }
    }

}