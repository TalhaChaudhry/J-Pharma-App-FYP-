package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.AdminPendingOrderCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.SamplePendingOrdersItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;

import java.text.MessageFormat;

public class AdminPendingOrdersAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    AdminPendingOrderCallbacks callback;

    public AdminPendingOrdersAdapter(Context context, AdminPendingOrderCallbacks callback) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SamplePendingOrdersItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SamplePendingOrdersItemBinding binding = (SamplePendingOrdersItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.dateTv.setText(getItem(position).getDateAndTime());
        binding.shopAddressTv.setText(getItem(position).getUserModel().getAddress());
        int total = 0;
        for (CartModel cartModel : getItem(position).getOrdersList()) {
            total += cartModel.getQuantity() * cartModel.getModel().getPrice();
        }
        binding.shopNameTv.setText(MessageFormat.format("{0}", total));
        binding.cancelTv.setOnClickListener(view -> callback.onCancelOrderClicker(getItem(holder.getAdapterPosition())));
        binding.dispatchTv.setOnClickListener(view -> callback.onDispatchOrderClicker(getItem(holder.getAdapterPosition())));
        binding.proceedingTv.setOnClickListener(view -> callback.onProceedOrderClicker(getItem(holder.getAdapterPosition())));
        holder.itemView.setOnClickListener(view -> callback.onClickedToView(getItem(holder.getAdapterPosition())));
    }
}
