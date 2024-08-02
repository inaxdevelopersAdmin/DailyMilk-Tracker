package com.inaxdevelopers.mydailymilk.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inaxdevelopers.mydailymilk.model.CustomerAmountMilkPrice;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert
    void insert(CustomerModel model);

    @Update
    void update(CustomerModel model);

    @Delete
    void Delete(CustomerModel model);

    @Query("DELETE FROM customer_table")
    void deleteAllCustomer();

    @Query("SELECT * FROM customer_table ORDER BY CustomerName ASC")
    LiveData<List<CustomerModel>> getAllCustomer();


    @Query("SELECT Amount FROM customer_table WHERE CustomerName = :customerName")
     String  getAmountByCustomerName(String customerName);
    @Query("UPDATE customer_table SET Amount = :newAmount WHERE CustomerName = :customerName")
    void updateAmountByCustomerName(String customerName, String newAmount);

    @Query("SELECT * FROM customer_table ORDER BY id ASC")
    LiveData<List<CustomerModel>>  getCustomerById();

}
