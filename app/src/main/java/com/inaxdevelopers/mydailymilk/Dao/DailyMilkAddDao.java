package com.inaxdevelopers.mydailymilk.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.model.DailyAdd;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;

import java.util.List;

@Dao
public interface DailyMilkAddDao {
    @Insert
    void insertNew(DailyMilkAdd model);
    @Query("SELECT * FROM dailymilkadd_table ORDER BY TotalAmount ASC")
    LiveData<List<DailyMilkAdd>> getAllAmount();

    @Query("SELECT * FROM dailymilkadd_table ORDER BY DailyId = :milkMame")
    LiveData<List<DailyMilkAdd>> getDataID(int milkMame);

}
