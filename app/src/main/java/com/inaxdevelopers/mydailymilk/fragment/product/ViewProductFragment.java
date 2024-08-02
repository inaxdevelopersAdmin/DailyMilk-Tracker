package com.inaxdevelopers.mydailymilk.fragment.product;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.product.DailyMilkAddAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentViewProductBinding;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

public class ViewProductFragment extends Fragment {

    FragmentViewProductBinding binding;
    MilkViewModel milkViewModel;
    DailyMilkAddAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewProductBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        milkViewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        milkViewModel.getMilk().observe(getActivity(), milkModels -> {
            if (!milkModels.isEmpty()) {
                binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new DailyMilkAddAdapter(getActivity(), milkModels);
                binding.recyclerview.setAdapter(adapter);
                binding.setMilkViewModel(milkViewModel);
                binding.setLifecycleOwner(this);
                binding.animationView.setVisibility(View.GONE);
            } else {
                binding.animationView.setVisibility(View.VISIBLE);
            }
        });
    }
}