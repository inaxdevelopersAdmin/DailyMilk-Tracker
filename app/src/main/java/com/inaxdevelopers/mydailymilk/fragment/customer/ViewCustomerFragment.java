package com.inaxdevelopers.mydailymilk.fragment.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.customer.ViewCustomerAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentViewCustomerBinding;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.viewmodel.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerFragment extends Fragment {
    FragmentViewCustomerBinding binding;
    ViewCustomerAdapter adapter;
    CustomerViewModel model;
    CustomerModel customerModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewCustomerBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(this).get(CustomerViewModel.class);
        SETAdapter();
        binding.SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });
    }

    private void SETAdapter() {
        model.GetAllCustomerData().observe(getActivity(), customerModels -> {
            if (!customerModels.isEmpty()) {
                binding.showAllCustomer.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ViewCustomerAdapter(getActivity(), customerModels);
                binding.showAllCustomer.setAdapter(adapter);
                binding.setCustomerModel(customerModel);
                binding.setLifecycleOwner(this);
                binding.animationView.setVisibility(View.GONE);
            } else {
                Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
                binding.animationView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void filterData(String query) {
        model.GetAllCustomerData().observe(this, customerModels -> {
            List<CustomerModel> filteredList = new ArrayList<>();
            for (CustomerModel model : customerModels) {
                if (model.getCustomerName().contains(query) || model.getCustomerAddress().contains(query) || model.getCustomerAddressID().contains(query) || model.getCustomerName().contains(query)) {
                    filteredList.add(model);
                }
            }
            if (!filteredList.isEmpty()) {
                binding.showAllCustomer.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter.SearchCustomer(filteredList);
                binding.setLifecycleOwner(this);
            } else {
                binding.showAllCustomer.setAdapter(null);
                Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}