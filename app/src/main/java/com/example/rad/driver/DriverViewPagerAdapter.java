package com.example.rad.driver;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DriverViewPagerAdapter extends FragmentStateAdapter {
    public DriverViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DriverTrackingFragment();
            case 1:
                return new DriverPickupFragment();
            case 2:
                return new DriverDropoffFragment();
            case 3:
                return new DriverProfileFragment();
            default:
                return new DriverTrackingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
