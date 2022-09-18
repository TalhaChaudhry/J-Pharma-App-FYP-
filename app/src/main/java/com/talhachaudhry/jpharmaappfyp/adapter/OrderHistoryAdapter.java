package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.CancelledOrdersCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleCompleteOrderItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;

import java.text.MessageFormat;

public class OrderHistoryAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {

    Context context;
    CancelledOrdersCallback callback;

    public OrderHistoryAdapter(Context context, CancelledOrdersCallback callback) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleCompleteOrderItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleCompleteOrderItemBinding binding = (SampleCompleteOrderItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.dateTv.setText(getItem(position).getDateAndTime());
        int total = 0;
        for (CartModel cartModel : getItem(position).getOrdersList()) {
            total += cartModel.getQuantity() * cartModel.getModel().getPrice();
        }
        binding.shopNameTv.setText(MessageFormat.format("{0}", total));
        holder.itemView.setOnClickListener(view -> callback.onItemClicked(getItem(holder.getAdapterPosition())));
    }
}
