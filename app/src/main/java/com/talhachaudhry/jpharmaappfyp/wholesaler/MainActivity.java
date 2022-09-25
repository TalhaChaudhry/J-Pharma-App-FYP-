package com.talhachaudhry.jpharmaappfyp.wholesaler;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.talhachaudhry.jpharmaappfyp.adapter.CustomMenuAdapter;
import com.talhachaudhry.jpharmaappfyp.adapter.InvoiceDetailAdapter;
import com.talhachaudhry.jpharmaappfyp.callbacks.OpenBillFragmentCallback;
import com.talhachaudhry.jpharmaappfyp.login_details.Login;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.models.CartModel;
import com.talhachaudhry.jpharmaappfyp.models.OrderModel;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.InvoiceFragment;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.MainFragment;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityMainBinding;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.PrivacyPolicyFragment;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.UserProfileFragment;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Objects;

import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener, OpenBillFragmentCallback {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    private DuoMenuView mDuoMenuView;
    CustomMenuAdapter mMenuAdapter;
    InvoiceDetailAdapter adapter;
    ArrayList<String> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        auth = FirebaseAuth.getInstance();
        mDuoMenuView = (DuoMenuView) binding.drawer.getMenuView();
        init();
        setMenuList();
        handleMenu();
        handleDrawer();
        goToFragment(MainFragment.newInstance(), false);
    }

    void init() {
        adapter = new InvoiceDetailAdapter(this);
        binding.invoiceDetailRv.setAdapter(adapter);
        binding.crossIconIv.setOnClickListener(view -> onBackPressed());
    }

    private void setMenuList() {
        menuList.add("Dashboard");
        menuList.add("Profile");
        menuList.add("Invoice");
        menuList.add("Privacy Policy");
    }

    private void handleMenu() {
        mMenuAdapter = new CustomMenuAdapter(menuList);
        mMenuAdapter.setViewSelected(0, true);
        mDuoMenuView.setOnMenuClickListener(this);
        mDuoMenuView.setAdapter(mMenuAdapter);
    }


    @Override
    public void onFooterClicked() {
        auth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    @Override
    public void onHeaderClicked() {
        // do nothing
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        mMenuAdapter.setViewSelected(position, true);
        switch (position) {
            case 0:
                setTitle("J Pharma");
                goToFragment(new MainFragment(), false);
                break;
            case 1:
                setTitle("Profile");
                goToFragment(UserProfileFragment.newInstance(), true);
                break;
            case 2:
                setTitle("Invoice");
                goToFragment(new InvoiceFragment(), true);
                break;
            case 3:
                setTitle("Privacy Policy");
                goToFragment(PrivacyPolicyFragment.newInstance(), true);
                break;
            default:
                goToFragment(new MainFragment(), true);
                break;
        }
        binding.drawer.closeDrawer();
    }

    public void showDialogue() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure, you want to exit ")
                .setPositiveButton("Yes", (dialog, which) ->
                        finishAffinity())
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                binding.drawer,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawer.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (binding.billBar.getVisibility() == View.VISIBLE) {
            binding.billBar.setVisibility(View.INVISIBLE);
        } else if (!getSupportFragmentManager().popBackStackImmediate()) {
            showDialogue();
        }
    }

    @Override
    public void openBill(OrderModel model) {
        binding.billBar.setVisibility(View.VISIBLE);
        adapter.submitList(model.getOrdersList());
        binding.dateTv.setText(model.getDateAndTime());
        binding.orderIdTv.setText(model.getOrderId());
        int total = 0;
        for (CartModel cartModel : model.getOrdersList()) {
            total += cartModel.getQuantity() * cartModel.getModel().getPrice();
        }
        binding.totalPriceTextView.setText(MessageFormat.format("{0}", total));
    }
}