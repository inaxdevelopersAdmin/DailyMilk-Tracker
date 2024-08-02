package com.inaxdevelopers.mydailymilk.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.inaxdevelopers.mydailymilk.adapter.ViewPagerAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentProductBinding;
import com.inaxdevelopers.mydailymilk.fragment.product.AddProductFragment;
import com.inaxdevelopers.mydailymilk.fragment.product.ViewProductFragment;


public class ProductFragment extends Fragment {
    FragmentProductBinding binding;
    ViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new AddProductFragment(), "Add Product");
        adapter.addFrag(new ViewProductFragment(), "View Product");
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}