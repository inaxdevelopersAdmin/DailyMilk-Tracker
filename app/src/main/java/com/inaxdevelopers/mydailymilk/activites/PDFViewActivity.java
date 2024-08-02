package com.inaxdevelopers.mydailymilk.activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.databinding.ActivityPdfviewBinding;

import java.io.File;

public class PDFViewActivity extends AppCompatActivity {
    ActivityPdfviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityPdfviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String fileName = getIntent().getStringExtra("show");
        Log.e("TAG", "onCreate: "+fileName);
        SetData(fileName);
        SetToolbar(fileName);
    }

    private void SetToolbar(String fileName) {
        binding.toolbar.setTitle("Pdf View");
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        binding.share.setOnClickListener(v -> {
            if (fileName != null && !fileName.isEmpty()) {
                File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
                if (pdfFile.exists()) {
                    Log.e("TAG", "onCreate: "+pdfFile);
                    sharePdfFile(pdfFile);
                } else {
                    File pdfFile1 = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
                    if (pdfFile1.exists()) {
                        Log.e("TAG", "onCreate: "+pdfFile1);

                        sharePdfFile(pdfFile1);
                    } else {
                        Toast.makeText(this, "File Not Share Yet", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                File pdfFile1 = new File(fileName);
                if (pdfFile1.exists()) {
                    sharePdfFile(pdfFile1);
                }
            }
        });
    }
    private void SetData(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
            if (pdfFile.exists()) {
                Log.e("TAG", "onCreate: "+pdfFile);
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromFile(pdfFile).swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load();
            } else {
                File pdfFile1 = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
                if (pdfFile1.exists()) {
                    Log.e("TAG", "onCreate: "+pdfFile1);

                    PDFView pdfView = findViewById(R.id.pdfView);
                    pdfView.fromFile(pdfFile1).swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .load();
                }
            }
        } else {
            File pdfFile1 = new File(fileName);
            if (pdfFile1.exists()) {
                Log.e("TAG", "onCreate: "+pdfFile1);
                PDFView pdfView = findViewById(R.id.pdfView);
                pdfView.fromFile(pdfFile1).swipeHorizontal(false)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load();
            }
        }
    }

    private void sharePdfFile(File pdfFile) {
        Log.e("TAG", "onCreate: "+pdfFile);
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share PDF"));
    }

}