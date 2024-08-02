package com.inaxdevelopers.mydailymilk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "Customer_table", foreignKeys = @ForeignKey(entity = MilkModel.class, parentColumns = "id",childColumns = "Milk_id", onDelete = ForeignKey.CASCADE))
public class CustomerModel  implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id ;
   @ColumnInfo(name = "Milk_id")
    int Milk_id;
    @ColumnInfo(name = "CustomerName")
    String CustomerName;
    @ColumnInfo(name = "CustomerAddressID")
    String CustomerAddressID;
    @ColumnInfo(name = "CustomerAddress")
    String CustomerAddress;
    @ColumnInfo(name = "CustomerNumber")
    String CustomerNumber;
    @ColumnInfo(name = "CustomerType")
    String CustomerType;
    @ColumnInfo(name = "Amount")
    String Amount;
    @ColumnInfo(name = "Milk")
    String Milk;
    @ColumnInfo(name = "MilkPrice")
    String MilkPrice;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getMilk_id() {
        return Milk_id;
    }

    public void setMilk_id(int milk_id) {
        Milk_id = milk_id;
    }
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getCustomerNumber() {
        return CustomerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        CustomerNumber = customerNumber;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }
    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
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

    public String getCustomerAddressID() {
        return CustomerAddressID;
    }

    public void setCustomerAddressID(String customerAddressID) {
        CustomerAddressID = customerAddressID;
    }
}


