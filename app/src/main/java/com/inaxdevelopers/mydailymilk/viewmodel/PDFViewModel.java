package com.inaxdevelopers.mydailymilk.viewmodel;

import android.content.Context;
import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inaxdevelopers.mydailymilk.model.PDFFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFViewModel extends ViewModel {

    private LiveData<List<PDFFile>> pdfFiles;

    public PDFViewModel() {
    }

    public LiveData<List<PDFFile>> getPDFFilesCustomer(Context context) {
        MutableLiveData<List<PDFFile>> data = new MutableLiveData<>();
        List<PDFFile> pdfFiles = new ArrayList<>();
        File directory = new File(String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)));
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(".pdf")) {
                    pdfFiles.add(new PDFFile(file.getName(), file.getAbsolutePath()));
                }
            }
        }

        data.setValue(pdfFiles);
        return data;
    }

    public LiveData<List<PDFFile>> getPDFFilesMilk(Context context) {
        MutableLiveData<List<PDFFile>> data = new MutableLiveData<>();
        List<PDFFile> pdfFiles = new ArrayList<>();
        File directory = new File(String.valueOf(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)));
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().toLowerCase().endsWith(".pdf")) {
                    pdfFiles.add(new PDFFile(file.getName(), file.getAbsolutePath()));
                }
            }
        }

        data.setValue(pdfFiles);
        return data;
    }

}
