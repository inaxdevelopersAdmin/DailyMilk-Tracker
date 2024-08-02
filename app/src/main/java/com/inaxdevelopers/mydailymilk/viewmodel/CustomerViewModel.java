package com.inaxdevelopers.mydailymilk.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inaxdevelopers.mydailymilk.database.CustomerDatabase;
import com.inaxdevelopers.mydailymilk.Dao.CustomerDao;
import com.inaxdevelopers.mydailymilk.Dao.DailyAddDao;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.model.DailyAdd;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    public CustomerDao customerDao;
    public DailyAddDao dailyAddDao;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        CustomerDatabase database = CustomerDatabase.getDatabaseInstance(application);
        CustomerDao customerDao = database.customerDao();
        DailyAddDao addDao = database.DailyAddDao();
        this.customerDao = customerDao;
        this.dailyAddDao = addDao;
    }
    public void insertMilk(CustomerModel model) {
        this.customerDao.insert(model);
    }
    public LiveData<List<CustomerModel>> GetAllCustomerData() {
        return customerDao.getAllCustomer();
    }
    public void updateAmountByCustomerName(String customerName, String newAmount) {
        AsyncTask.execute(() -> customerDao.updateAmountByCustomerName(customerName, newAmount));
    }
    public void insertDaily(DailyAdd dailyAdd) {
        this.dailyAddDao.insertPurchase(dailyAdd);
    }
    public LiveData<List<DailyAdd>> getCustomerWithDailyAdd(int customerId) {
        return dailyAddDao.getCustomerWithDailyAdd(customerId);
    }
}
