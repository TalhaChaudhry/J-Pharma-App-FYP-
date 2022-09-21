package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.text.InputType;
import android.text.method.BaseKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentUserProfileBinding;
import com.talhachaudhry.jpharmaappfyp.login_details.Login;
import com.talhachaudhry.jpharmaappfyp.models.UserModel;
import com.talhachaudhry.jpharmaappfyp.view_models.EditUserProfileViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserProfileFragment extends Fragment {

    FragmentUserProfileBinding binding;
    EditUserProfileViewModel viewModel;
    boolean condition;
    ProgressDialog progressDialog;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading Profile");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait while data is loading ... ");
        progressDialog.show();
        condition = !binding.addressTv.getText().toString().trim().equals("") && !binding.contactTv.getText().toString().trim().equals("")
                && !binding.shopNameTv.getText().toString().trim().equals("") && !binding.cityTv.getText().toString().trim().equals("")
                && !binding.nameTv.getText().toString().trim().equals("");
        ;
        binding.editBtn.setOnClickListener(view -> {
            if (binding.editBtn.getText().toString().toLowerCase().equals("edit")) {
                binding.editBtn.setText(R.string.apply);
                setEnabling(true);
            } else if (condition) {
                Integer.parseInt(String.valueOf(binding.contactTv.getText()).replace("\"'", ""));
                setEnabling(false);
                progressDialog.setTitle("Editing Profile");
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Making Changes ....");
                progressDialog.show();
                binding.editBtn.setText(R.string.edit);
                viewModel.upDateUserModel(binding.shopNameTv.getText().toString(),
                        binding.nameTv.getText().toString(), binding.cityTv.getText().toString(),
                        binding.contactTv.getText().toString(), binding.addressTv.getText().toString());
            } else {
                Toast.makeText(requireActivity(),
                                "No field can be empty",
                                Toast.LENGTH_SHORT).
                        show();
            }
        });
        viewModel = new ViewModelProvider(requireActivity()).get(EditUserProfileViewModel.class);
        viewModel.getUserModelMutableLiveData().observe(getViewLifecycleOwner(), this::dataFilling);
        viewModel.getNotifyUser().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Toast.makeText(requireActivity(), "Failed to upload profile picture ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), "Failed to upload edit profile", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });
        viewModel.getProfileImage().observe(getViewLifecycleOwner(), bytes -> {
                    Glide.with(requireActivity()).
                            load(bytes).
                            placeholder(R.drawable.avatar).
                            into(binding.userImage);
                    progressDialog.dismiss();
                }
        );
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
        setEnabling(false);
        progressDialog.dismiss();
    }

    void setEnabling(boolean flag) {
        if (flag) {
            binding.nameTv.setKeyListener(textBaseKeyListener);
            binding.cityTv.setKeyListener(textBaseKeyListener);
            binding.addressTv.setKeyListener(textBaseKeyListener);
            binding.shopNameTv.setKeyListener(textBaseKeyListener);
            binding.contactTv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        } else {
            binding.nameTv.setKeyListener(null);
            binding.cityTv.setKeyListener(null);
            binding.addressTv.setKeyListener(null);
            binding.shopNameTv.setKeyListener(null);
            binding.contactTv.setInputType(InputType.TYPE_NULL);
            binding.editBtn.setText(R.string.edit);
        }
    }

    void onProfileClicked() {
        new AlertDialog.Builder(requireActivity())
                .setTitle("Profile Image")
                .setMessage("View or Upload Profile Image")
                .setCancelable(true)
                .setNegativeButton("Upload", (dialogInterface, i) -> {
                    Intent videoIntent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    launchSomeActivity.launch(Intent.createChooser(videoIntent, "Select an Image"));
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
                        progressDialog.setTitle("Uploading Profile Picture");
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Uploading ....");
                        progressDialog.show();
                        viewModel.uploadImage(data);
                    } catch (Exception e) {
                        // Log the exception
                        e.printStackTrace();
                        Toast.makeText(requireActivity(), "Something went wrong",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

    BaseKeyListener textBaseKeyListener = new BaseKeyListener() {
        @Override
        public int getInputType() {
            return InputType.TYPE_CLASS_TEXT;
        }
    };

    BaseKeyListener numberBaseKeyListener = new BaseKeyListener() {
        @Override
        public int getInputType() {
            return InputType.TYPE_NUMBER_FLAG_DECIMAL;
        }
    };

}