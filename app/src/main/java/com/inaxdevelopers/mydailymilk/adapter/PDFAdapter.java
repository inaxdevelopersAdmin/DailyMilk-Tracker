package com.inaxdevelopers.mydailymilk.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.activites.PDFViewActivity;
import com.inaxdevelopers.mydailymilk.databinding.ItemPdfBinding;
import com.inaxdevelopers.mydailymilk.model.PDFFile;
import com.inaxdevelopers.mydailymilk.utils.PDFFileManager;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private List<PDFFile> pdfFiles;
    Context context;

    public PDFAdapter() {

    }

    public void setPDFFiles(List<PDFFile> pdfFiles, Context context) {
        this.pdfFiles = pdfFiles;
        this.context = context;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPdfBinding binding = ItemPdfBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PDFViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        PDFFile pdfFile = pdfFiles.get(position);
        holder.Bind(pdfFile);
        holder.itemView.setOnClickListener(v -> {
            context.startActivity(new Intent(context, PDFViewActivity.class).putExtra("show", pdfFile.getPath()));
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to Delete This file ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String path = pdfFile.getPath();
                                PDFFileManager.deletePDFFile(path);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }


    static class PDFViewHolder extends RecyclerView.ViewHolder {
        ItemPdfBinding binding;

        public PDFViewHolder(@NonNull ItemPdfBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }

        public void Bind(PDFFile pdfFile) {
            binding.setPDFFile(pdfFile);
            binding.executePendingBindings();

        }
    }
}
