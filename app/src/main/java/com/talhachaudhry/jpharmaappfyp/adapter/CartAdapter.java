package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.callbacks.CartCallback;
import com.talhachaudhry.jpharmaappfyp.databinding.SampleItemCartBinding;


import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.EditOrderActivity;

import java.text.MessageFormat;

public class CartAdapter extends ListAdapter<CartModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    CartCallback callback;

    public CartAdapter(Context context, CartCallback callback) {
        super(new DiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SampleItemCartBinding.
                inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SampleItemCartBinding binding = (SampleItemCartBinding) holder.binding;
        binding.name.setText(getItem(position).getModel().getName());
        binding.medicineDetail.setText(getItem(position).getModel().getDetail());
        binding.quantity.setText(MessageFormat.format("{0}", getItem(position).getQuantity()));
        Glide.with(context).
                load(Uri.parse(getItem(position).getModel().getImagePath())).
                placeholder(R.drawable.sample_image).
                into(binding.medicineImage);
        if (context instanceof EditOrderActivity)
            binding.delete.setVisibility(View.GONE);
        binding.delete.setOnClickListener(view -> callback.onDeleteClicked(getItem(holder.getAdapterPosition())));
        binding.quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!String.valueOf(charSequence).trim().equals("") && Integer.parseInt(String.valueOf(charSequence)) != 0) {
                    getItem(holder.getAdapterPosition()).setQuantity(Integer.parseInt(String.valueOf(charSequence)));
                } else {
                    binding.quantity.setText("1");
                    getItem(holder.getAdapterPosition()).setQuantity(1);
                    Toast.makeText(context, "Quantity cannot be empty or 0", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
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
