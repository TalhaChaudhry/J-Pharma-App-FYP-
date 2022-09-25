package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleInvoiceDetailItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;

import java.text.MessageFormat;

public class InvoiceDetailAdapter extends ListAdapter<CartModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;


    public InvoiceDetailAdapter(Context context) {
        super(new DiffUtils());
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleInvoiceDetailItemBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleInvoiceDetailItemBinding binding = (SampleInvoiceDetailItemBinding) holder.binding;
        binding.medicineName.setText(getItem(position).getModel().getName());
        binding.quantityAndPrice.setText(MessageFormat.format("{0} X {1}", getItem(position).getQuantity(),
                getItem(position).getModel().getPrice()));
        binding.totalTv.setText(MessageFormat.format("{0}",
                (getItem(position).getQuantity() * getItem(position).getModel().getPrice())));
    }

    private static class DiffUtils extends DiffUtil.ItemCallback<CartModel> {

        @Override
        public boolean areItemsTheSame(@NonNull CartModel oldItem, @NonNull CartModel newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartModel oldItem, @NonNull CartModel newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    }
}
