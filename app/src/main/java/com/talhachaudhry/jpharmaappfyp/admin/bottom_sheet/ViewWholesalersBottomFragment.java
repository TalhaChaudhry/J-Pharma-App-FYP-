package com.talhachaudhry.jpharmaappfyp.admin.bottom_sheet;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentViewWholesalersBottomBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;

public class ViewWholesalersBottomFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "model";
    FragmentViewWholesalersBottomBinding binding;
    private UserModel model;

    public static ViewWholesalersBottomFragment newInstance(UserModel model) {
        ViewWholesalersBottomFragment fragment = new ViewWholesalersBottomFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewWholesalersBottomBinding.inflate(inflater, container, false);
        binding.nameTv.setText(model.getUserName());
        binding.shopNameTv.setText(model.getShopName());
        binding.cityTv.setText(model.getCity());
        binding.addressTv.setText(model.getAddress());
        binding.contactTv.setText(model.getContact());
        Glide.with(requireActivity()).
                load(Uri.parse(model.getProfilePic())).
                placeholder(R.drawable.avatar).
                into(binding.userImage);
        return binding.getRoot();
    }
}