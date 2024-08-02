package com.inaxdevelopers.mydailymilk.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inaxdevelopers.mydailymilk.MyApp;
import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.database.CustomerDatabase;
import com.inaxdevelopers.mydailymilk.databinding.FragmentHomeBinding;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.utils.SessionManager;
import com.inaxdevelopers.mydailymilk.viewmodel.CustomerViewModel;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    double totalAmount = 0.0;
    double totalMilkPrice = 0.0;
    private SessionManager sessionManager;
    MilkViewModel viewModel;

    CustomerViewModel customerViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        String userRole = sessionManager.getUserRole();
//        if (userRole.equals("admin")) {
//            binding.admin.setVisibility(View.VISIBLE);
//        } else if (userRole.equals("user")) {
//            binding.admin.setVisibility(View.GONE);
//        }
        IDSetValue();
    }

    private void IDSetValue() {
        CustomerDatabase database = CustomerDatabase.getDatabaseInstance(getActivity().getApplicationContext());
        database.customerDao().getAllCustomer().observe(getActivity(), new Observer<List<CustomerModel>>() {
            @Override
            public void onChanged(List<CustomerModel> customerModels) {
                double positiveCount = 0.0;
                double negativeCount = 0.0;
                for (CustomerModel customer : customerModels) {
                    if (!customer.getAmount().isEmpty()) {
                        String amount = customer.getAmount();
                        if (!amount.isEmpty()) {
                            double amountValue = Double.parseDouble(amount);
                            if (amountValue > 0) {
                                positiveCount += amountValue;
                            } else if (amountValue < 0) {
                                negativeCount += amountValue;
                            }
                        }
                        updateTextView(positiveCount, negativeCount);
                    }
                }
            }
        });

        database.milkDao().getAllMilkName().observe(getActivity(), dailyMilkAdds -> {
            double positiveCount = 0.0;
            double negativeCount = 0.0;
            for (MilkModel model : dailyMilkAdds) {
                if (!model.getMilkQuantity().isEmpty()) {
                    double MilkQuantity = Double.parseDouble(model.getMilkQuantity());
                    double MilkPrice = Double.parseDouble(model.getMilkPrice());
                    double amountValue = MilkPrice *MilkQuantity;
                    if (amountValue > 0) {
                        positiveCount += amountValue;
                    } else if (amountValue < 0) {
                        negativeCount += amountValue;
                    }
                }
                updateTextViewA(positiveCount, negativeCount);
            }
        });
    }

    private void updateTextViewA(double positiveCount, double negativeCount) {
        String countText = String.valueOf(positiveCount + negativeCount);
//        binding.mlIncome.setText("₹" + String.valueOf(positiveCount));
        binding.mlExpenses.setText("₹" + String.valueOf(countText));
        binding.mlTotalBalance.setText("₹" + countText);
    }

    private void updateTextView(double positiveCount, double negativeCount) {
        binding.csIncome.setText("₹" + String.valueOf(positiveCount));
        binding.csExpenses.setText("₹" + String.valueOf(negativeCount));
        String countText = String.valueOf(positiveCount + negativeCount);
        binding.csTotalBalance.setText("₹" + countText);
    }
}