package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentUserProfileBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;
import com.talhachaudhry.jpharmaappfyp.view_models.EditUserProfileViewModel;

public class UserProfileFragment extends Fragment {

    FragmentUserProfileBinding binding;
    EditUserProfileViewModel viewModel;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(EditUserProfileViewModel.class);
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), model ->
                dataFilling(model));
        binding.userImage.setOnClickListener(view -> onProfileClicked());
        return binding.getRoot();
    }

    void dataFilling(UserModel model) {
        Glide.with(requireActivity()).
                load(Uri.parse(model.getProfilePic())).
                placeholder(R.drawable.avatar).
                into(binding.userImage);
        binding.nameTv.setText(model.getUserName());
        binding.contactTv.setText(model.getContact());
        binding.shopNameTv.setText(model.getShopName());
        binding.addressTv.setText(model.getAddress());
        binding.contactTv.setText(model.getContact());
    }

    void onProfileClicked() {
        //TODO
    }
}