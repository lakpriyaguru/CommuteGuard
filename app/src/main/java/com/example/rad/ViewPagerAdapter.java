package com.example.rad;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ClientDashboardFragment();
            case 1:
                return new ClientLocationFragment();
            case 2:
                return new ClientPaymentFragment();
            case 3:
                return new ClientProfileFragment();
            default:
                return new ClientDashboardFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
