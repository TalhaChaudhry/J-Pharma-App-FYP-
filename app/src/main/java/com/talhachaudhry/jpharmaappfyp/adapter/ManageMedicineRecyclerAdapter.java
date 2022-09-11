package com.talhachaudhry.jpharmaappfyp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewMedicineDetail;
import com.talhachaudhry.jpharmaappfyp.databinding.ManageMedicineItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;

public class ManageMedicineRecyclerAdapter extends ListAdapter<ManageMedicineModel, RecyclerViewViewHolderBoilerPlate> {

    Context context;
    OnViewMedicineDetail callback;

    public ManageMedicineRecyclerAdapter(Context context, OnViewMedicineDetail callback) {
        super(new DiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(ManageMedicineItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        ManageMedicineItemBinding binding = (ManageMedicineItemBinding) holder.binding;
        binding.medicineDetail.setText(getItem(position).getDetail());
        binding.name.setText(getItem(position).getName());
        Glide.with(context).
                load(Uri.parse(getItem(position).getImagePath())).
                placeholder(context.getDrawable(R.drawable.sample_image)).
                into(binding.medicineImage);
        binding.delete.setOnClickListener(view -> callback.deleteMedicine(getItem(holder.getAdapterPosition())));
        binding.update.setOnClickListener(view -> callback.updateMedicine(getItem(holder.getAdapterPosition())));
        binding.viewDetails.setOnClickListener(view ->
                callback.onViewMedicineDetailClicked(getItem(holder.getAdapterPosition())));
    }

    private static class DiffUtils extends DiffUtil.ItemCallback<ManageMedicineModel> {

        @Override
        public boolean areItemsTheSame(@NonNull ManageMedicineModel oldItem, @NonNull ManageMedicineModel newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ManageMedicineModel oldItem, @NonNull ManageMedicineModel newItem) {
            return oldItem.toString().equals(newItem.toString());
        }
    }
}
