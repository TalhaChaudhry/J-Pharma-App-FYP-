package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.talhachaudhry.jpharmaappfyp.databinding.SampleOrderViewItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;

import java.text.MessageFormat;
import java.util.List;

public class OrdersDetailAdapter extends RecyclerView.Adapter<RecyclerViewViewHolderBoilerPlate> {

    Context context;
    List<CartModel> list;

    public OrdersDetailAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleOrderViewItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleOrderViewItemBinding binding = (SampleOrderViewItemBinding) holder.binding;
        binding.priceTv.setText(MessageFormat.format("{0}", list.get(position).getModel().getPrice()));
        binding.medicineName.setText(list.get(position).getModel().getName());
        binding.quantityTv.setText(MessageFormat.format("{0}", list.get(position).getQuantity()));
        int total = list.get(position).getQuantity() * list.get(position).getModel().getPrice();
        binding.totalTv.setText(MessageFormat.format("{0}", total));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
