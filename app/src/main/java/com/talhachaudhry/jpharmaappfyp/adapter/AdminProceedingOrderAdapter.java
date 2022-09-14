package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.AdminDispatchOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.callbacks.AdminProceedingCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleAdminDispatchedOrdersItemBinding;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleProceedingItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;

public class AdminProceedingOrderAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    AdminProceedingCallbacks callback;

    public AdminProceedingOrderAdapter(Context context, AdminProceedingCallbacks callback) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleProceedingItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleProceedingItemBinding binding = (SampleProceedingItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.shopNameTv.setText(getItem(position).getUserModel().getUserName());
        binding.cancelTv.setOnClickListener(view -> callback.onCancelClicked(getItem(holder.getAdapterPosition())));
        binding.dispatchTv.setOnClickListener(view -> callback.onDispatchClicked(getItem(holder.getAdapterPosition())));
        binding.pendingOrderRv.setOnClickListener(view -> callback.onPendingClicked(getItem(holder.getAdapterPosition())));
        holder.itemView.setOnClickListener(view -> callback.onClickedToView(getItem(holder.getAdapterPosition())));
    }
}