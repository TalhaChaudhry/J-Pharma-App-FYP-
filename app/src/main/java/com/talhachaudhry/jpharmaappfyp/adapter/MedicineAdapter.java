package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnItemClicked;
import com.talhachaudhry.jpharmaappfyp.models.MedicineModel;
import com.talhachaudhry.jpharmaappfyp.R;

import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    Context context;
    ArrayList<MedicineModel> list;
    OnItemClicked callBack;

    public MedicineAdapter(Context context, ArrayList<MedicineModel> list, OnItemClicked callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_placeorder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.medicine_name.setText(list.get(position).getMedicineName());
        holder.medicine_detail.setText(list.get(position).getMedicineDetail());
        Glide.with(context).
                load(list.get(position).getImagePath()).
                placeholder(R.drawable.sample_image).
                into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.setOnItemClicked(list.get(holder.getAdapterPosition()).getMedicineName(), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView medicine_name;
        TextView medicine_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.medicine_image);
            medicine_name = itemView.findViewById(R.id.medicine_name);
            medicine_detail = itemView.findViewById(R.id.medicine_detail);
        }
    }
}
