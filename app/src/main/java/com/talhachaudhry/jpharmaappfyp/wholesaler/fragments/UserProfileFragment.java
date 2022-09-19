package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentUserProfileBinding;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;
import com.talhachaudhry.jpharmaappfyp.view_models.EditUserProfileViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileFragment extends Fragment {

    FragmentUserProfileBinding binding;
    EditUserProfileViewModel viewModel;
    boolean condition;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        condition = !binding.addressTv.getText().toString().trim().equals("") && !binding.contactTv.getText().toString().trim().equals("")
                && !binding.shopNameTv.getText().toString().trim().equals("") && !binding.cityTv.getText().toString().trim().equals("")
                && !binding.nameTv.getText().toString().trim().equals("");
        binding.editBtn.setOnClickListener(view -> {
            if (binding.editBtn.getText().toString().toLowerCase().equals("edit")) {
                binding.editBtn.setText(R.string.apply);
                setEnabling(true);
            } else {
                if (condition) {
                    setEnabling(false);
                    binding.editBtn.setText(R.string.edit);
                    viewModel.upDateUserModel(binding.shopNameTv.getText().toString(),
                            binding.nameTv.getText().toString(), binding.cityTv.getText().toString(),
                            binding.contactTv.getText().toString(), binding.addressTv.getText().toString());
                } else {
                    Toast.makeText(requireActivity(), "No field can be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel = new ViewModelProvider(requireActivity()).get(EditUserProfileViewModel.class);
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), this::dataFilling);
        viewModel.getProfileImage().observe(getViewLifecycleOwner(), bytes -> {
            Glide.with(requireActivity()).
                    load(bytes).
                    placeholder(R.drawable.avatar).
                    into(binding.userImage);
        });
        binding.userImage.setOnClickListener(view -> onProfileClicked());
        return binding.getRoot();
    }

    void dataFilling(UserModel model) {
        if (model.getIsInStorage() != 1) {
            Glide.with(requireActivity()).
                    load(Uri.parse(model.getProfilePic())).
                    placeholder(R.drawable.avatar).
                    into(binding.userImage);
        }
        binding.nameTv.setText(model.getUserName());
        binding.contactTv.setText(model.getContact());
        binding.shopNameTv.setText(model.getShopName());
        binding.addressTv.setText(model.getAddress());
        binding.cityTv.setText(model.getCity());
        new Handler(Looper.getMainLooper()).post(() -> setEnabling(false));
    }

    void setEnabling(boolean flag) {
        binding.nameTv.setEnabled(flag);
        binding.contactTv.setEnabled(flag);
        binding.shopNameTv.setEnabled(flag);
        binding.addressTv.setEnabled(flag);
        binding.cityTv.setEnabled(flag);
    }

    void onProfileClicked() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Your Alert")
                .setMessage("Your Message")
                .setCancelable(true)
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // TODO
                    }
                })
                .setNegativeButton("Upload", (dialogInterface, i) -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launchSomeActivity.launch(intent);
                }).show();
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        // Setting image on image view using Bitmap
                        Bitmap bitmap = MediaStore
                                .Images
                                .Media
                                .getBitmap(
                                        requireActivity().getContentResolver(),
                                        result.getData().getData());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();
                        viewModel.uploadImage(data);
                    } catch (Exception e) {
                        // Log the exception
                        e.printStackTrace();
                        Toast.makeText(requireActivity(), "Something went wrong",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
}