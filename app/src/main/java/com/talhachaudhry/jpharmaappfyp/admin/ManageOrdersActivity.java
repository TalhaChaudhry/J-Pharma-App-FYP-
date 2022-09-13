package com.talhachaudhry.jpharmaappfyp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.talhachaudhry.jpharmaappfyp.admin.fragments.CancelledOrdersFragment;
import com.talhachaudhry.jpharmaappfyp.admin.fragments.DispatchedOrdersAdminFragment;
import com.talhachaudhry.jpharmaappfyp.admin.fragments.PendingOrdersAdminFragment;
import com.talhachaudhry.jpharmaappfyp.databinding.ActivityManageOrdersBinding;

import java.util.Objects;

public class ManageOrdersActivity extends AppCompatActivity {

    ActivityManageOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding.viewpager.setAdapter(new FragmentsAdapter(getSupportFragmentManager(), getLifecycle()));
        tabSettings(binding.tabLayout, binding.viewpager);
    }

    public void tabSettings(TabLayout tabLayout, ViewPager2 viewPager2) {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do nothing
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private static class FragmentsAdapter extends FragmentStateAdapter {


        public FragmentsAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return PendingOrdersAdminFragment.newInstance();
                case 1:
                    return DispatchedOrdersAdminFragment.newInstance();
                case 2:
                    return CancelledOrdersFragment.newInstance();
                default:
                    return PendingOrdersAdminFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}