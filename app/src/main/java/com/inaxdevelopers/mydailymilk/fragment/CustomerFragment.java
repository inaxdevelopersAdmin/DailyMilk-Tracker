package com.inaxdevelopers.mydailymilk.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inaxdevelopers.mydailymilk.adapter.PDFAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentCustomerBinding;
import com.inaxdevelopers.mydailymilk.viewmodel.PDFViewModel;

public class CustomerFragment extends Fragment {
    private PDFViewModel pdfViewModel;
    private PDFAdapter pdfAdapter;
    FragmentCustomerBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(getActivity().getLayoutInflater(), container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pdfViewModel = new ViewModelProvider(this).get(PDFViewModel.class);
        binding.customerDataPdf.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        pdfViewModel.getPDFFilesCustomer(getContext()).observe(getActivity(), pdfFiles -> {
          if (!pdfFiles.isEmpty())
          {
              pdfAdapter.setPDFFiles(pdfFiles, getActivity());
              binding.animationView.setVisibility(View.GONE);
              pdfAdapter = new PDFAdapter();
              binding.customerDataPdf.setAdapter(pdfAdapter);
          }else {
              binding.animationView.setVisibility(View.VISIBLE);
          }
        });
    }
}
