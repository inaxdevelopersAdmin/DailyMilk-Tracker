package com.inaxdevelopers.mydailymilk.fragment.customer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.databinding.FragmentCustomerAddBinding;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.viewmodel.CustomerViewModel;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAddFragment extends Fragment {
    FragmentCustomerAddBinding binding;
    CustomerViewModel customerViewModel;
    MilkViewModel milkViewModel;
    ArrayAdapter<CharSequence> CustomerTypeAdapter;
    String selectedCustomerType;
    String selectedMilkName;
    String selectedMilkPrice;
    int selectedID;
    String TotalAmount;
    List<String> milkNames = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerAddBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        milkViewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        GetMilkData();
        SetSpinner();
        IdClick();
    }

    private void GetMilkData() {
        milkViewModel.getMilk().observe(getViewLifecycleOwner(), new Observer<List<MilkModel>>() {
            @Override
            public void onChanged(List<MilkModel> milkModels) {
                ArrayAdapter<String> milkAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout);
                if (milkModels != null && !milkModels.isEmpty()) {
                    Map<String, Pair<Integer, String>> milkMap = new HashMap<>();
                    for (MilkModel milkModel : milkModels) {
                        int milkId = milkModel.getId();
                        String milkName = milkModel.getMilkName();
                        String milkPrice = milkModel.getMilkPrice();
                        milkAdapter.add(milkName);
                        milkMap.put(milkName, new Pair<>(milkId, milkPrice));
                    }
                    binding.MilkType.setTag(milkMap);
                } else {

                }
                binding.MilkType.setAdapter(milkAdapter);
            }
        });

        binding.MilkType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMilkName1 = parent.getItemAtPosition(position).toString();
                Map<String, Pair<Integer, String>> milkMap = (Map<String, Pair<Integer, String>>) binding.MilkType.getTag();
                if (milkMap != null) {
                    Pair<Integer, String> selectedMilkData = milkMap.get(selectedMilkName1);
                    if (selectedMilkData != null) {
                        int selectedMilkId = selectedMilkData.first;
                        String selectedMilkPrice1 = selectedMilkData.second;
                        if (!selectedMilkName1.isEmpty() && !selectedMilkPrice1.isEmpty()) {
                            selectedMilkName = selectedMilkName1;
                            selectedMilkPrice = selectedMilkPrice1;
                            selectedID = selectedMilkId;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void SetSpinner() {
        CustomerTypeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.CustomerType, R.layout.spinner_layout);
        CustomerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.CustomerType.setAdapter(CustomerTypeAdapter);
        CustomerTypeAdapter.notifyDataSetChanged();
        binding.CustomerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!CustomerTypeAdapter.isEnabled(position)) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.disabled, Toast.LENGTH_SHORT).show();
                } else {
                    milkNames = Collections.singletonList(String.valueOf(CustomerTypeAdapter.getItem(position)));
                    selectedCustomerType = String.valueOf(CustomerTypeAdapter.getItem(position));
                    if (selectedCustomerType.equals("Card")) {
                        binding.customerCard.setVisibility(View.VISIBLE);
                        binding.customerDeposit.setVisibility(View.GONE);
                    } else if (selectedCustomerType.equals("Deposit")) {
                        binding.customerDeposit.setVisibility(View.VISIBLE);
                        binding.customerCard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void IdClick() {
        binding.AddUser.setOnClickListener(v -> {
            if (selectedCustomerType.equals("Card")) {
                String amount = binding.customerCardAmount.getText().toString().trim();
                TotalAmount = amount;
            } else if (selectedCustomerType.equals("Deposit")) {
                String amounts = binding.customerDepositAmount.getText().toString().trim();
                TotalAmount = amounts;
            }
            AddData();
            String amount = TotalAmount;
            CustomerModel model = new CustomerModel();
            model.setMilk_id(selectedID);
            model.setCustomerName(binding.customerName.getText().toString().trim());
            model.setCustomerNumber(binding.customerNumber.getText().toString().trim());
            model.setCustomerAddressID(binding.customerAddress.getText().toString().trim());
            model.setCustomerAddress(binding.customerAddress.getText().toString().trim());
            model.setCustomerType(selectedCustomerType);
            model.setMilk(selectedMilkName);
            model.setMilkPrice(selectedMilkPrice);
            model.setAmount(amount);
            customerViewModel.insertMilk(model);
            Toast.makeText(getActivity(), R.string.CustomerName_add, Toast.LENGTH_SHORT).show();
            binding.customerName.setText("");
            binding.customerNumber.setText("");
            binding.customerAddress.setText("");
            binding.customerFlat.setText("");
            binding.customerCardAmount.setText("");
            binding.customerDepositAmount.setText("");

        });
        binding.RemoveUser.setOnClickListener(v -> {
            binding.customerName.setText("");
            binding.customerNumber.setText("");
            binding.customerAddress.setText("");
            binding.customerFlat.setText("");
            binding.customerCardAmount.setText("");
            binding.customerDepositAmount.setText("");
        });
    }

    private void AddData() {

    }
}