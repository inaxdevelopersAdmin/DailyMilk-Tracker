package com.inaxdevelopers.mydailymilk.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.inaxdevelopers.mydailymilk.model.MilkModel;

import java.util.List;

@Dao
public interface MilkDao {
    @Insert
    void insert(MilkModel model);

    @Query("UPDATE Milk_table SET MilkQuantity=:UpDateMilkQuantity WHERE id=:id")
    void update(String UpDateMilkQuantity, int id);

    @Query("SELECT * FROM Milk_table ORDER BY MilkName ASC")
    LiveData<List<MilkModel>> getAllMilkName();
}
