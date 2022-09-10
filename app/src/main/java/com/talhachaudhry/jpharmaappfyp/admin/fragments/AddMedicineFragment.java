package com.talhachaudhry.jpharmaappfyp.admin.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentAddMedicineBinding;
import com.talhachaudhry.jpharmaappfyp.models.ManageMedicineModel;
import com.talhachaudhry.jpharmaappfyp.models.MedicineModel;
import com.talhachaudhry.jpharmaappfyp.view_models.ManageMedicineViewModel;

import java.text.MessageFormat;

public class AddMedicineFragment extends Fragment {

    private static final String ARG_PARAM1 = "model";

    private ManageMedicineModel model;
    ManageMedicineViewModel viewModel;
    FragmentAddMedicineBinding binding;

    public static AddMedicineFragment newInstance(ManageMedicineModel model) {
        AddMedicineFragment fragment = new AddMedicineFragment();
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

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddMedicineBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ManageMedicineViewModel.class);
        if (model != null) {
            binding.imageTv.setText(model.getImagePath());
            binding.title.setText(R.string.edit_medicine_title);
            binding.nameTv.setEnabled(false);
            binding.detailTv.setText(model.getDetail());
            binding.mgTv.setText(model.getMg());
            binding.nameTv.setText(model.getName());
            binding.priceTv.setText(MessageFormat.format("{0}", model.getPrice()));
            Glide.with(requireActivity()).
                    load(Uri.parse(model.getImagePath())).
                    placeholder(requireActivity().getDrawable(R.drawable.sample_image)).
                    into(binding.medicineImageView);
        }
        binding.imageTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do  nothing
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                StringBuilder sb=new StringBuilder(charSequence);
                Glide.with(requireActivity()).
                        load(Uri.parse(sb.toString())).
                        placeholder(requireActivity().getDrawable(R.drawable.sample_image)).
                        into(binding.medicineImageView);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
        binding.backBtn.setOnClickListener(view -> requireActivity().getSupportFragmentManager().popBackStack());
        binding.doneBtn.setOnClickListener(view -> {
            if (!binding.nameTv.getText().toString().trim().equals("") &&
                    !binding.detailTv.getText().toString().trim().equals("") &&
                    !binding.priceTv.getText().toString().trim().equals("") &&
                    !binding.imageTv.getText().toString().trim().equals("") &&
                    !binding.mgTv.getText().toString().trim().equals("")) {
                try {
                    if (model == null) {
                        ManageMedicineModel manageMedicineModel = new ManageMedicineModel(binding.imageTv.getText().toString().trim(),
                                binding.detailTv.getText().toString().trim(),
                                Integer.parseInt(binding.priceTv.getText().toString()),
                                binding.mgTv.getText().toString().trim(),
                                binding.nameTv.getText().toString().trim());
                        viewModel.addMedicine(manageMedicineModel);
                    } else {
                        model.setDetail(binding.detailTv.getText().toString().trim());
                        model.setImagePath(binding.imageTv.getText().toString().trim());
                        model.setPrice(Integer.parseInt(binding.priceTv.getText().toString()));
                        model.setMg(binding.mgTv.getText().toString().trim());
                        viewModel.updateMedicine(model);
                    }
                    requireActivity().getSupportFragmentManager().popBackStack();

                } catch (NumberFormatException e) {
                    Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireActivity(), "Field cannot be Empty", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}