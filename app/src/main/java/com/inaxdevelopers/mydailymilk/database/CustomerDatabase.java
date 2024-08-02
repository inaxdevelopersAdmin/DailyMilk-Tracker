package com.inaxdevelopers.mydailymilk.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.inaxdevelopers.mydailymilk.Dao.DailyAddDao;
import com.inaxdevelopers.mydailymilk.Dao.DailyMilkAddDao;
import com.inaxdevelopers.mydailymilk.Dao.MilkDao;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.Dao.CustomerDao;
import com.inaxdevelopers.mydailymilk.model.DailyAdd;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;
import com.inaxdevelopers.mydailymilk.model.MilkModel;

@Database(entities = {CustomerModel.class, DailyAdd.class, MilkModel.class, DailyMilkAdd.class}, version = 1, exportSchema = false)
public abstract class CustomerDatabase extends RoomDatabase {

    private static CustomerDatabase instance;
    public  abstract CustomerDao customerDao();
    public  abstract DailyAddDao DailyAddDao();
    public abstract MilkDao milkDao();
    public abstract DailyMilkAddDao dailyMilkAddDao();
    public static CustomerDatabase getDatabaseInstance(Context context) {
        if (instance == null) {
            instance = (CustomerDatabase) Room.databaseBuilder(context.getApplicationContext(), CustomerDatabase.class, "Customer_Database").allowMainThreadQueries().build();
        }
        return instance;
    }
}