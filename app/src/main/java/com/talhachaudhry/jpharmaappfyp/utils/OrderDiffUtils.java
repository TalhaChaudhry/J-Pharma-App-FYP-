package com.talhachaudhry.jpharmaappfyp.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;

public class OrderDiffUtils extends DiffUtil.ItemCallback<OrderModel> {


    @Override
    public boolean areItemsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
        return oldItem.hashCode() == newItem.hashCode();
    }

    @Override
    public boolean areContentsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
        return oldItem.toString().equals(newItem.toString());
    }
}