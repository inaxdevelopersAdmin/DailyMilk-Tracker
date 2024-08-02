package com.inaxdevelopers.mydailymilk.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.inaxdevelopers.mydailymilk.utils.PdfGenerator;
import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.product.DailyDataAdapter;
import com.inaxdevelopers.mydailymilk.databinding.ActivityDailyMilkDataEntryBinding;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyMilkDataEntry extends AppCompatActivity {

    ActivityDailyMilkDataEntryBinding binding;
    int id;
    MilkModel model;
    MilkViewModel milkViewModel;
    DailyDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDailyMilkDataEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        milkViewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        Intent intent = getIntent();
        model = (MilkModel) intent.getSerializableExtra("Data");
        id = model.getId();
        SetData();
        Set_Toolbar();
        ShowData(model.getId());
    }

    private void Set_Toolbar() {
        binding.toolbar.setTitle("My Daily Milk");
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        binding.share.setOnClickListener(v -> {
            Log.e("TAG", "ShareFsssssssssssssile: "+id);
            ShareFile(id);
        });
    }

    private void ShareFile(int id) {
        milkViewModel.getDataByDailyDId(id).observe(this, dailyMilkAdds -> {
            Log.e("TAG", "ShareFile: "+id);
            PdfGenerator.generatePdfFromRoomDataMilk(this, dailyMilkAdds, dailyMilkAdds.get(id).getDailyMilkName(), dailyMilkAdds.get(id).getTotalAmount());
            String generatedFileName = PdfGenerator.getGeneratedFileNameMilk();
            if (!generatedFileName.isEmpty()) {
                Intent intent = new Intent(this, PDFViewActivity.class).putExtra("show", generatedFileName);
                startActivity(intent);
            }
        });
    }

    private void SetData() {
        binding.showMilkName.setText(model.getMilkName());
        binding.milkPrice.setText(model.getMilkPrice());
        binding.showMilkQuantity.setText(model.getMilkQuantity());
        binding.calendarViewId.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = (month + 1) + "-" + dayOfMonth + "-" + year;
            showPopupDialog(selectedDate);
        });
    }

    private void showPopupDialog(String selectedDate) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        EditText dialogEditText = dialogView.findViewById(R.id.dialogEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setTitle("Enter Quantity of Milk")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = dialogEditText.getText().toString();
                        Update_Milk_old_Table(model, userInput);
                        String price = String.valueOf(Double.parseDouble(model.getMilkPrice()) * Double.parseDouble(userInput));
                        String updatedAmount = String.valueOf(price);
                        AddData(selectedDate, model.getId(), model.getMilkName(), userInput, model.getMilkPrice(), updatedAmount);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void Update_Milk_old_Table(MilkModel model, String userInput) {
        int userInputData = Integer.parseInt(userInput);
        int modelData = Integer.parseInt(model.getMilkQuantity());
        int UpDateData = userInputData + modelData;
        String update = String.valueOf(UpDateData);
        milkViewModel.milkDao.update(update,model.getId());
       }

    private void AddData(String selectedDate, int id, String milkName, String milkQuantity, String price, String TotalPrice) {
        DailyMilkAdd model = new DailyMilkAdd();
        model.setDate(selectedDate);
        model.setDaily_id(id);
        model.setDailyMilkName(milkName);
        model.setDailyQuantity(milkQuantity);
        model.setDailyMilkPrice(price);
        model.setTotalAmount(TotalPrice);
        milkViewModel.AddData(model);
    }

    private void ShowData(int id) {
        milkViewModel.getDataByDailyDId(id).observe(this, dailyMilkAdds -> {
            List<DailyMilkAdd> filteredList = new ArrayList<>();
            for (DailyMilkAdd model : dailyMilkAdds) {
                if (model.getDaily_id() == id) {
                    filteredList.add(model);
                }
            }
            if (!filteredList.isEmpty()) {
                binding.dailyDataMilk.setLayoutManager(new LinearLayoutManager(this));
                adapter = new DailyDataAdapter(this, filteredList);
                binding.dailyDataMilk.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}