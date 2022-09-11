package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentAddToCartBinding;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.view_models.PlaceOrderViewModel;

import java.text.MessageFormat;

public class AddToCartFragment extends Fragment {

    FragmentAddToCartBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private ManageMedicineModel model;
    PlaceOrderViewModel viewModel;

    public static AddToCartFragment newInstance(ManageMedicineModel model) {
        AddToCartFragment fragment = new AddToCartFragment();
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
        binding = FragmentAddToCartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(PlaceOrderViewModel.class);
        binding.nameTv.setText(model.getName());
        binding.priceTv.setText(MessageFormat.format("{0}", model.getPrice()));
        binding.mgTv.setText(model.getMg());
        binding.detailTv.setText(model.getDetail());
        Glide.with(requireActivity()).
                load(Uri.parse(model.getImagePath())).
                placeholder(R.drawable.sample_image).
                into(binding.medicineImageView);
        binding.backBtn.setOnClickListener(view -> requireActivity().onBackPressed());
        binding.addToCartBtn.setOnClickListener(view -> {
            if (!binding.quantity.getText().toString().trim().equals("")
                    && Integer.parseInt(binding.quantity.getText().toString()) != 0) {
                viewModel.addToCart(new CartModel(model, Integer.parseInt(binding.quantity.getText().toString())));
                requireActivity().onBackPressed();
            } else {
                Toast.makeText(requireActivity(), "Quantity cannot be empty or 0", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}