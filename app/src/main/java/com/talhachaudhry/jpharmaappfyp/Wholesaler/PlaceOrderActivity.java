package com.talhachaudhry.jpharmaappfyp.Wholesaler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.talhachaudhry.jpharmaappfyp.Adapter.MedicineAdapter;
import com.talhachaudhry.jpharmaappfyp.Callbacks.OnItemClicked;
import com.talhachaudhry.jpharmaappfyp.Models.MedicineModel;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityPlaceOrderBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaceOrderActivity extends AppCompatActivity implements OnItemClicked {

    ActivityPlaceOrderBinding binding;
    ArrayList<MedicineModel> list = new ArrayList<>();
    MedicineAdapter adapter;
    String[] languages = {"Panadol", "Disprin", "Paracitamol", "Bruffin", "Vagra", "Vitamins"};
    ArrayAdapter arrayAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaceOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Place Order");
        setList();
        arrayAdapter = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, languages);
        binding.searchEt.setAdapter(arrayAdapter);
        binding.searchEt.setThreshold(1);
        adapter = new MedicineAdapter(this, list, this);
        binding.placeOrderRv.setAdapter(adapter);
        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = Arrays.asList(languages).indexOf(binding.searchEt.getText().toString().trim());
                binding.placeOrderRv.scrollToPosition(pos);
            }
        });

    }

    public void setList() {
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4OVZowtZ3QpQ98SXGx9OmnQX0GmlA9gpPcg&usqp=CAU",
                "Panadol",
                "It is a very good medicine Order ot to get it "));
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfK78VoAG1xxxz4y7NjWQBbBZRN6RGSpkzlQ&usqp=CAU",
                "Disprin",
                "It is a very good medicine Order ot to get it "));
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRUf2TZ_hzM_w0F8t-LvMtFmMOIzZLiel-A1g&usqp=CAU",
                "Paracitamol",
                "It is a very good medicine Order ot to get it "));
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZUt-p16rR8YKK9g-sI9mSd4H4HXB4JLxJGg&usqp=CAU",
                "Bruffin",
                "It is a very good medicine Order ot to get it "));
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_MJk865wCm-EZdkafiSyCGjdG7Sf9puHevA&usqp=CAU",
                "Vagra",
                "It is a very good medicine Order ot to get it "));
        list.add(new MedicineModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVGZ6SgDbGoTZU6p_F6RxxApX24W-hB8Vcyw&usqp=CAU",
                "Vitamins",
                "It is a very good medicine Order ot to get it "));

    }

    @Override
    public void setOnItemClicked(String itemName, int position) {

    }
}