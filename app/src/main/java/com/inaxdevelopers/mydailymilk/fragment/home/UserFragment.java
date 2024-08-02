package com.inaxdevelopers.mydailymilk.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.ViewPagerAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentUserBinding;
import com.inaxdevelopers.mydailymilk.fragment.customer.CustomerAddFragment;
import com.inaxdevelopers.mydailymilk.fragment.customer.ViewCustomerFragment;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;
    ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new ViewCustomerFragment(),getActivity().getResources().getString(R.string.view_customer));
        adapter.addFrag(new CustomerAddFragment(), getActivity().getResources().getString(R.string.add_customer));
        binding.viewpager.setAdapter(adapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}