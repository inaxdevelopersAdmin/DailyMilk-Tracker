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

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.PDFAdapter;
import com.inaxdevelopers.mydailymilk.databinding.FragmentMilkBinding;
import com.inaxdevelopers.mydailymilk.viewmodel.PDFViewModel;

public class MilkFragment extends Fragment {
    private PDFViewModel pdfViewModel;
    private PDFAdapter pdfAdapter;
    FragmentMilkBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMilkBinding.inflate(getActivity().getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pdfViewModel = new ViewModelProvider(this).get(PDFViewModel.class);
        binding.milkDataPdf.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        pdfViewModel.getPDFFilesMilk(getContext()).observe(getActivity(), pdfFiles -> {
            if (!pdfFiles.isEmpty()) {
                binding.animationView.setVisibility(View.GONE);
                pdfAdapter = new PDFAdapter();
                pdfAdapter.setPDFFiles(pdfFiles, getActivity());
                binding.milkDataPdf.setAdapter(pdfAdapter);
            } else {
                binding.animationView.setVisibility(View.VISIBLE);
            }
        });
    }
}