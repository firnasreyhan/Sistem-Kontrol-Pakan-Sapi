package com.android.sistemkontrolpakansapi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedWeightScaleAdapter extends RecyclerView.Adapter<FeedWeightScaleAdapter.ViewHolder> {
    private static List<FeedWeightScaleModel> list;
    private String myFormat = "dd-MM-yyyy"; //In which you need put here
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, new Locale("id", "ID"));
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

    public FeedWeightScaleAdapter(List<FeedWeightScaleModel> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed_weight_scale, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewDay.setText(list.get(position).getDay());
        holder.textViewDate.setText(formatTanggal(list.get(position).getTimestamp()));
        holder.textViewWeight.setText(list.get(position).getWeight() + " Kg");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDay, textViewDate, textViewWeight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewWeight = itemView.findViewById(R.id.textViewWeight);
        }
    }

    public String formatTanggal(String s) {
        String tanggal = "";
        try {
            Date date = dateFormat.parse(s);
            tanggal = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tanggal;
    }
}
