package com.talhachaudhry.jpharmaappfyp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnViewWholesalerClicked;
import com.talhachaudhry.jpharmaappfyp.databinding.ViewWholesalerListItemBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ViewWholesalersViewModel;

public class ViewWholesalerRecyclerAdapter extends ListAdapter<UserModel, RecyclerViewViewHolderBoilerPlate> {
    Context context;
    OnViewWholesalerClicked callback;

    public ViewWholesalerRecyclerAdapter(Context context, OnViewWholesalerClicked callback) {
        super(new DiffUtils());
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderBoilerPlate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewViewHolderBoilerPlate(ViewWholesalerListItemBinding.inflate(LayoutInflater.from(context),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderBoilerPlate holder, int position) {
        ViewWholesalerListItemBinding binding = (ViewWholesalerListItemBinding) holder.binding;
        binding.addressTv.setText(getItem(position).getAddress());
        binding.nameTv.setText(getItem(position).getUserName());
        binding.shopNameTv.setText(getItem(position).getShopName());
        binding.cityTv.setText(getItem(position).getCity());
        if (getItem(position).getIsInStorage() != 1) {
            Glide.with(context).
                    load(Uri.parse(getItem(position).getProfilePic())).
                    placeholder(R.drawable.avatar).
                    into(binding.userImage);
        }
        holder.itemView.setOnClickListener(view -> callback.onViewWholesalerClicked(getItem(holder.getAdapterPosition())));
    }

    private static class DiffUtils extends DiffUtil.ItemCallback<UserModel> {
        @Override
        public boolean areItemsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {return oldItem.hashCode() == newItem.hashCode();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {return oldItem.toString().equals(newItem.toString());
        }
    }
}
