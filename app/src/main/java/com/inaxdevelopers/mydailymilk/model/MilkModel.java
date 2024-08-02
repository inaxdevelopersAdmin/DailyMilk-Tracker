package com.inaxdevelopers.mydailymilk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "Milk_table")
public class MilkModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "MilkName")
    String milkName;
    @ColumnInfo(name = "MilkQuantity")
    String milkQuantity;
    @ColumnInfo(name = "MilkPrice")
    String milkPrice;
    @ColumnInfo(name = "ImagePath")
    public String milkImagePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMilkName() {
        return milkName;
    }

    public void setMilkName(String milkName) {
        this.milkName = milkName;
    }

    public String getMilkQuantity() {
        return milkQuantity;
    }

    public void setMilkQuantity(String milkQuantity) {
        this.milkQuantity = milkQuantity;
    }

    public String getMilkPrice() {
        return milkPrice;
    }

    public void setMilkPrice(String milkPrice) {
        this.milkPrice = milkPrice;
    }

    public String getMilkImagePath() {
        return milkImagePath;
    }

    public void setMilkImagePath(String milkImagePath) {
        this.milkImagePath = milkImagePath;
    }


}
