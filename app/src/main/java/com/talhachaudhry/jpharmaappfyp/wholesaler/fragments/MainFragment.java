package com.talhachaudhry.jpharmaappfyp.wholesaler.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.talhachaudhry.jpharmaappfyp.adapter.ItemsRecyclerAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.OnItemClicked;
import com.talhachaudhry.jpharmaappfyp.models.ItemsModel;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.wholesaler.EditOrderActivity;
import com.talhachaudhry.jpharmaappfyp.wholesaler.OrdersHistoryActivity;
import com.talhachaudhry.jpharmaappfyp.wholesaler.PendingOrdersActivity;
import com.talhachaudhry.jpharmaappfyp.wholesaler.PlaceOrderActivity;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment implements OnItemClicked {


    FragmentMainBinding binding;
    ArrayList<ItemsModel> list = new ArrayList<>();
    ItemsRecyclerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        setList();
        adapter = new ItemsRecyclerAdapter(getContext(), list, this);
        binding.wholesalerRv.setAdapter(adapter);
        return binding.getRoot();
    }

    public void setList() {
        list.add(new ItemsModel("Place Order", R.drawable.place_order));
        list.add(new ItemsModel("Edit Order", R.drawable.edit_order));
        list.add(new ItemsModel("Pending Orders", R.drawable.pending_order));
        list.add(new ItemsModel("Orders History", R.drawable.order_history));
    }

    @Override
    public void setOnItemClicked(String itemName, int position) {
        switch (position) {
            case 0:
                getContext().startActivity(new Intent(getContext(), PlaceOrderActivity.class));
                break;
            case 1:
                getContext().startActivity(new Intent(getContext(), EditOrderActivity.class));
                break;
            case 2:
                getContext().startActivity(new Intent(getContext(), PendingOrdersActivity.class));
                break;
            case 3:
                getContext().startActivity(new Intent(getContext(), OrdersHistoryActivity.class));
                break;
        }
    }
}