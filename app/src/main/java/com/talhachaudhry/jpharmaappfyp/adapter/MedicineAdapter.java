package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewMedicineDetail;
import com.talhachaudhry.jpharmaappfyp.databinding.SamplePlaceorderItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.R;

public class MedicineAdapter extends ListAdapter<ManageMedicineModel,RecyclerViewViewHolderBoilerPlate> {
    Context context;
    OnViewMedicineDetail callBack;

    public MedicineAdapter(Context context, OnViewMedicineDetail callBack) {
        super(new ManageMedicineRecyclerAdapter.DiffUtils());
        this.context = context;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(SamplePlaceorderItemBinding.
                inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        SamplePlaceorderItemBinding binding= (SamplePlaceorderItemBinding) holder.binding;
        binding.medicineName.setText(getItem(position).getName());
        binding.medicineDetail.setText(getItem(position).getDetail());
        Glide.with(context).
                load(getItem(position).getImagePath()).
                placeholder(R.drawable.sample_image).
                into(binding.medicineImage);
        holder.itemView.setOnClickListener(view ->
                callBack.onViewMedicineDetailClicked(getItem(holder.getAdapterPosition())));
    }
}
