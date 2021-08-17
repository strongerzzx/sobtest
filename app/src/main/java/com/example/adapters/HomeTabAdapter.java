package com.example.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.beans.resultbeans.CategoryData;
import com.example.fragments.HomeSubTabFragment;


import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentStateAdapter {

    private List<CategoryData> mList = new ArrayList<>();

    public HomeTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HomeSubTabFragment.Companion.getInstance(mList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(@Nullable List<CategoryData> it) {
        mList.clear();
        mList.addAll(it);
    }
}
