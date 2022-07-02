package com.talhachaudhry.jpharmaappfyp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.talhachaudhry.jpharmaappfyp.Adapter.ItemsRecyclerAdapter;
import com.talhachaudhry.jpharmaappfyp.Callbacks.OnItemClicked;
import com.talhachaudhry.jpharmaappfyp.LoginDetails.Login;
import com.talhachaudhry.jpharmaappfyp.Models.ItemsModel;
import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityAdminBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements OnItemClicked {

    ActivityAdminBinding binding;
    ArrayList<ItemsModel> list = new ArrayList<>();
    ItemsRecyclerAdapter adapter;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin Panel");
        auth = FirebaseAuth.getInstance();
        setList();
        adapter = new ItemsRecyclerAdapter(this, list, this);
        binding.adminRv.setAdapter(adapter);
    }

    public void setList() {
        list.add(new ItemsModel("Manage Medicine", R.drawable.medicine));
        list.add(new ItemsModel("Manage Order", R.drawable.orders));
        list.add(new ItemsModel("View Wholesaler", R.drawable.wholesaler));
        list.add(new ItemsModel("Sales Report", R.drawable.sales_report));
    }

    @Override
    public void setOnItemClicked(String itemName, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, ManageMedicineActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ManageOrdersActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ViewWholesalerActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ViewWholesalerActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                auth.signOut();
                finish();
                startActivity(new Intent(AdminActivity.this, Login.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}