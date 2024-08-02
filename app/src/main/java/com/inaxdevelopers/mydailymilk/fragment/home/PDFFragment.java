package com.inaxdevelopers.mydailymilk.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inaxdevelopers.mydailymilk.adapter.ViewPagerAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentPDFBinding;
import com.inaxdevelopers.mydailymilk.fragment.CustomerFragment;
import com.inaxdevelopers.mydailymilk.fragment.MilkFragment;


public class PDFFragment extends Fragment {

    FragmentPDFBinding binding;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPDFBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new CustomerFragment(), "Customer Data");
        adapter.addFrag(new MilkFragment(), "Milk Data");
        binding.viewpagerPDF.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpagerPDF);
    }
}