package com.talhachaudhry.jpharmaappfyp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class RecyclerViewViewHolderBoilerPlate extends RecyclerView.ViewHolder {

    public ViewBinding binding;

    public RecyclerViewViewHolderBoilerPlate(@NonNull ViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
