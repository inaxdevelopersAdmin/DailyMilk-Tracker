package com.inaxdevelopers.mydailymilk.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.inaxdevelopers.mydailymilk.Dao.DailyMilkAddDao;
import com.inaxdevelopers.mydailymilk.Dao.MilkDao;
import com.inaxdevelopers.mydailymilk.database.CustomerDatabase;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;
import com.inaxdevelopers.mydailymilk.model.MilkModel;

import java.util.List;

public class MilkViewModel extends AndroidViewModel {

    public LiveData<MilkModel> GetAllMilk;
    public MilkDao milkDao;
    public DailyMilkAddDao DailyMilk;

    public MilkViewModel(@NonNull Application application) {
        super(application);
        CustomerDatabase database = CustomerDatabase.getDatabaseInstance(application);
        MilkDao milkDao1 = database.milkDao();
        DailyMilkAddDao milkAddDao = database.dailyMilkAddDao();
        this.milkDao = milkDao1;
        this.DailyMilk = milkAddDao;
    }

    public void insertMilk(MilkModel milkModel) {
        this.milkDao.insert(milkModel);
    }

    public LiveData<List<MilkModel>> getMilk() {
        return this.milkDao.getAllMilkName();
    }

    public void AddData(DailyMilkAdd model) {
        this.DailyMilk.insertNew(model);
    }
    public LiveData<List<DailyMilkAdd>> getDataByDailyDId(int id) {
        return DailyMilk.getDataID(id);
    }

}
