package com.inaxdevelopers.mydailymilk.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "DailyAdd_table", foreignKeys = @ForeignKey(entity = CustomerModel.class, parentColumns = "id",childColumns = "customer_id", onDelete = ForeignKey.CASCADE))
public class DailyAdd {
    @PrimaryKey(autoGenerate = true)
    int dailyAddId;
    int customer_id;
    String CustomerName;
    String CustomerType;
    String Date;
    String Milk;
    String MilkPrice;
    String TodayMilk;
    public String getTodayMilk() {
        return TodayMilk;
    }
    public void setTodayMilk(String todayMilk) {
        TodayMilk = todayMilk;
    }

    public int getDailyAddId() {
        return dailyAddId;
    }

    public void setDailyAddId(int dailyAddId) {
        this.dailyAddId = dailyAddId;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMilk() {
        return Milk;
    }

    public void setMilk(String milk) {
        Milk = milk;
    }

    public String getMilkPrice() {
        return MilkPrice;
    }

    public void setMilkPrice(String milkPrice) {
        MilkPrice = milkPrice;
    }
}
