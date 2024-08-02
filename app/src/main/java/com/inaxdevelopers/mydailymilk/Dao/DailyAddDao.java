package com.inaxdevelopers.mydailymilk.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.inaxdevelopers.mydailymilk.model.DailyAdd;

import java.util.List;

@Dao
public interface DailyAddDao {
    @Insert
    void insertPurchase(DailyAdd model);
    @Transaction
    @Query("SELECT * FROM DailyAdd_table WHERE customer_id =:id")
    LiveData<List<DailyAdd>> getCustomerWithDailyAdd(int id);
}
