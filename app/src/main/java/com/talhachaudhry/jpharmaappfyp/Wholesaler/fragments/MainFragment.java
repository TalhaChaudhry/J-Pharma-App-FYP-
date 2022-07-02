package com.talhachaudhry.jpharmaappfyp.Wholesaler.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.talhachaudhry.jpharmaappfyp.Adapter.ItemsRecyclerAdapter;
import com.talhachaudhry.jpharmaappfyp.Callbacks.OnItemClicked;
import com.talhachaudhry.jpharmaappfyp.Models.ItemsModel;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment  implements OnItemClicked {


//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";


//    private String mParam1;
//    private String mParam2;

    FragmentMainBinding binding;
    ArrayList<ItemsModel> list = new ArrayList<>();
    ItemsRecyclerAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                Toast.makeText(getContext(), "Clicked on Place Order", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getContext(), "Clicked on Edit Order", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getContext(), "Clicked on Pending Orders", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getContext(), "Clicked on Orders History", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}