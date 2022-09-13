package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.AdminDispatchOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleAdminDispatchedOrdersItemBinding;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleCancelledOrdersItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;

public class AdminDispatchedOrdersAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    AdminDispatchOrdersCallback callback;

    public AdminDispatchedOrdersAdapter(Context context, AdminDispatchOrdersCallback callback) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleAdminDispatchedOrdersItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleAdminDispatchedOrdersItemBinding binding = (SampleAdminDispatchedOrdersItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.shopNameTv.setText(getItem(position).getUserModel().getUserName());
        binding.cancelTv.setOnClickListener(view -> callback.onCancelClicked(getItem(holder.getAdapterPosition())));
        binding.completedTv.setOnClickListener(view -> callback.onCompleteClicked(getItem(holder.getAdapterPosition())));
        holder.itemView.setOnClickListener(view -> callback.onClickedToView(getItem(holder.getAdapterPosition())));
    }
}
