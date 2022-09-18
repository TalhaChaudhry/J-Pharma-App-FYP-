package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.callbacks.EditOrderCallbacks;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleEditOrderItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.utils.OrderDiffUtils;

import java.text.MessageFormat;

public class EditOrderAdapter extends ListAdapter<OrderModel, RecyclerViewViewHolderBoilerPlate> {

    Context context;
    EditOrderCallbacks callbacks;

    public EditOrderAdapter(Context context, EditOrderCallbacks callbacks) {
        super(new OrderDiffUtils());
        this.context = context;
        this.callbacks = callbacks;
    }

    public EditOrderAdapter(@NonNull AsyncDifferConfig<OrderModel> config, Context context, EditOrderCallbacks callbacks) {
        super(config);
        this.context = context;
        this.callbacks = callbacks;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleEditOrderItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleEditOrderItemBinding binding = (SampleEditOrderItemBinding) holder.binding;
        binding.orderIdTv.setText(getItem(position).getOrderId());
        binding.dateTv.setText(getItem(position).getDateAndTime());
        int total = 0;
        for (CartModel cartModel : getItem(position).getOrdersList()) {
            total += cartModel.getQuantity() * cartModel.getModel().getPrice();
        }
        binding.shopNameTv.setText(MessageFormat.format("{0}", total));
        binding.viewDetails.setOnClickListener(view -> callbacks.onViewOrderClicked(getItem(holder.getAdapterPosition())));
        binding.delete.setOnClickListener(view -> callbacks.onDeleteOrderClicked(getItem(holder.getAdapterPosition())));
        binding.update.setOnClickListener(view -> callbacks.onUpdateOrderClicked(getItem(holder.getAdapterPosition())));
    }
}
