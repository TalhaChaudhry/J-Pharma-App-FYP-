package com.talhachaudhry.jpharmaappfyp.wholesaler;


import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.talhachaudhry.jpharmaappfyp.adapter.CustomMenuAdapter;
import com.talhachaudhry.jpharmaappfyp.login_details.Login;

import com.talhachaudhry.jpharmaappfyp.R;
import com.talhachaudhry.jpharmaappfyp.wholesaler.fragments.MainFragment;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    private DuoMenuView mDuoMenuView;
    CustomMenuAdapter mMenuAdapter;
    ArrayList<String> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        mDuoMenuView = (DuoMenuView) binding.drawer.getMenuView();
        setMenuList();
        handleMenu();
        handleDrawer();
        goToFragment(MainFragment.newInstance(), false);
    }

    private void setMenuList() {
        menuList.add("Profile");
        menuList.add("Invoice");
        menuList.add("Privacy Policy");
        menuList.add("FAQ");
    }

    private void handleMenu() {
        mMenuAdapter = new CustomMenuAdapter(menuList);
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
        setTitle("J Pharma");

        mMenuAdapter.setViewSelected(position, true);

        switch (position) {
            default:
                goToFragment(new MainFragment(), false);
                break;
        }
        binding.drawer.closeDrawer();
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
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            this.finishAffinity();
        } else {
            super.onBackPressed();
        }
    }
}