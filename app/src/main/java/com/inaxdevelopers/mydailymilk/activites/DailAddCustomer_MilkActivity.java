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

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.adapter.customer.DailyDataAddCustomerMilkAdapter;
import com.inaxdevelopers.mydailymilk.database.CustomerDatabase;
import com.inaxdevelopers.mydailymilk.databinding.ActivityDailAddCustomerMilkBinding;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.model.DailyAdd;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.utils.PdfGenerator;
import com.inaxdevelopers.mydailymilk.viewmodel.CustomerViewModel;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailAddCustomer_MilkActivity extends AppCompatActivity {
    ActivityDailAddCustomerMilkBinding binding;
    CustomerViewModel customerViewModel;
    CustomerModel model;
    MilkViewModel milkModel;
    int id;
    DailyDataAddCustomerMilkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDailAddCustomerMilkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        milkModel = new ViewModelProvider(this).get(MilkViewModel.class);
        Intent intent = getIntent();
        model = (CustomerModel) intent.getSerializableExtra("Data");
        id = model.getId();
        ID_SetData();
        ID_SetToolbar();
        IDSEtAdapter(id);
    }

    private void ID_SetToolbar() {
        binding.toolbar.setTitle("Daily MilkAdd CustomerData");
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        binding.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        binding.share.setOnClickListener(v -> {
            String amount = model.getAmount();
            ShareFile(id, amount);
        });
    }

    private void ShareFile(int id, String amount) {
        customerViewModel.getCustomerWithDailyAdd(id).observe(this, dailyAdds -> {
            if (!dailyAdds.isEmpty()) {
                PdfGenerator.generatePdfFromRoomDataCustomer(DailAddCustomer_MilkActivity.this, dailyAdds, dailyAdds.get(id).getCustomerName(), amount);
                String generatedFileName = PdfGenerator.getGeneratedFileNameCustomer();
                if (!generatedFileName.isEmpty()) {
                    Intent intent = new Intent(this, PDFViewActivity.class).putExtra("show", generatedFileName);
                    startActivity(intent);
                }
            }
        });
    }

    private void ID_SetData() {
        binding.showCustomerName.setText(model.getCustomerName());
        binding.CustomerType.setText(model.getCustomerType());
        binding.MilkName.setText(model.getMilk());
        binding.MilkPric.setText(model.getMilkPrice());
        binding.calendarViewId.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            showPopupDialog(selectedDate);
        });
    }
    private void showPopupDialog(String selectedDate) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        EditText dialogEditText = dialogView.findViewById(R.id.dialogEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView).setTitle("Enter Quantity of Milk").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInput = dialogEditText.getText().toString();
                Update_OLD_Milk(model, milkModel, userInput);
                String price = String.valueOf(Double.parseDouble(model.getMilkPrice()) * Double.parseDouble(userInput));
                if (model.getCustomerType().equals("Card")) {
                    double updatedAmount = Double.parseDouble(model.getAmount()) - Double.parseDouble(price);
                    String updatedAmountString = String.valueOf(updatedAmount);
                    customerViewModel.updateAmountByCustomerName(model.getCustomerName(), updatedAmountString);
                    AddNewData(model.getId(), model.getCustomerName(), model.getCustomerType(), selectedDate, model.getMilk(), model.getMilkPrice(), userInput);

                } else if (model.getCustomerType().equals("Deposit")) {
                    double updatedAmount = Double.parseDouble(model.getAmount()) - Double.parseDouble(price);
                    String updatedAmountString = String.valueOf(updatedAmount);
                    customerViewModel.updateAmountByCustomerName(model.getCustomerName(), updatedAmountString);
                    AddNewData(model.getId(), model.getCustomerName(), model.getCustomerType(), selectedDate, model.getMilk(), model.getMilkPrice(), userInput);
                } else {
                    customerViewModel.updateAmountByCustomerName(model.getCustomerName(), price);
                    AddNewData(model.getId(), model.getCustomerName(), model.getCustomerType(), selectedDate, model.getMilk(), model.getMilkPrice(), userInput);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void Update_OLD_Milk(CustomerModel model, MilkViewModel milkModel, String userInput) {
        String Name = model.getMilk();
        String price = model.getMilkPrice();
        String MilkQuantity = "";
        Log.e("TAG", "Update_OLD_Milk: OLDNAME" + Name);
        Log.e("TAG", "Update_OLD_Milk:CSName " + price);

        CustomerDatabase database = CustomerDatabase.getDatabaseInstance(getApplicationContext());
        database.dailyMilkAddDao().getAllAmount().observe(this, dailyMilkAdds -> {
            for (DailyMilkAdd milkAdd : dailyMilkAdds) {
                if (!milkAdd.getDailyMilkName().isEmpty() && !milkAdd.getDailyMilkPrice().isEmpty()) {

                    if (milkAdd.getDailyMilkName().equals(Name) && milkAdd.getDailyMilkPrice().equals(price)) {
                        int ids = milkAdd.getDaily_id();
                        int userInputData = Integer.parseInt(userInput);
                        milkModel.getMilk().observe(this, milkModels -> {
                            String milk = milkModels.get(ids).getMilkQuantity();
//                            int modelData = Integer.parseInt(milk);
//                            int UpDateData =modelData-userInputData ;
//                            String update = String.valueOf(UpDateData);
                            Log.e("TAG", "milk " + milk);
//                            Log.e("TAG", "UpDateData " + UpDateData);
//                            Log.e("TAG", "update " + update);
//                            milkModel.milkDao.update(update, ids);
                        });
                    }
                }
            }
        });

    }

    public void AddNewData(int id, String customerName, String customerType, String selectedDate, String milk, String milkPrice, String TodayData) {
        DailyAdd add = new DailyAdd();
        add.setCustomer_id(id);
        add.setCustomerName(customerName);
        add.setCustomerType(customerType);
        add.setDate(selectedDate);
        add.setMilk(milk);
        add.setMilkPrice(milkPrice);
        add.setTodayMilk(TodayData);
        customerViewModel.insertDaily(add);
    }

    private void IDSEtAdapter(int id) {

        customerViewModel.getCustomerWithDailyAdd(id).observe(this, dailyAdds -> {
            List<DailyAdd> filteredList = new ArrayList<>();
            for (DailyAdd model : dailyAdds) {
                if (model.getCustomer_id() == this.id) {
                    filteredList.add(model);
                }
            }
            if (!filteredList.isEmpty()) {
                binding.dailyData.setLayoutManager(new LinearLayoutManager(this));
                adapter = new DailyDataAddCustomerMilkAdapter(this, filteredList);
                binding.dailyData.setAdapter(adapter);

            } else {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}