package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleCancelledOrdersItemBinding;

public class CancelledOrdersAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    CancelledOrdersCallback callback;

    public CancelledOrdersAdapter(Context context, CancelledOrdersCallback callback) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleCancelledOrdersItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleCancelledOrdersItemBinding binding = (SampleCancelledOrdersItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.reasonTv.setText(getItem(position).getReason());
        holder.itemView.setOnClickListener(view -> callback.onItemClicked(getItem(holder.getAdapterPosition())));
    }
}
