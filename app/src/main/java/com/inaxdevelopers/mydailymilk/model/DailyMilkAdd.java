package com.inaxdevelopers.mydailymilk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "DailyMilkAdd_table", foreignKeys = @ForeignKey(entity = MilkModel.class, parentColumns = "id", childColumns = "DailyId", onDelete = ForeignKey.CASCADE))
public class DailyMilkAdd {
   @PrimaryKey(autoGenerate = true)
   int id;
   @ColumnInfo(name = "DailyId")
   int Daily_id;
   @ColumnInfo(name = "Date")
   String date;
   @ColumnInfo(name = "DailyMilkName")
   String DailyMilkName;
   @ColumnInfo(name = "DailyMilkPrice")
   String DailyMilkPrice;
   @ColumnInfo(name = "DailyQuantity")
   public String DailyQuantity;
   @ColumnInfo(name = "TotalAmount")
   public String TotalAmount;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getDaily_id() {
      return Daily_id;
   }

   public void setDaily_id(int daily_id) {
      Daily_id = daily_id;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getDailyMilkName() {
      return DailyMilkName;
   }

   public void setDailyMilkName(String dailyMilkName) {
      DailyMilkName = dailyMilkName;
   }

   public String getDailyMilkPrice() {
      return DailyMilkPrice;
   }

   public void setDailyMilkPrice(String dailyMilkPrice) {
      DailyMilkPrice = dailyMilkPrice;
   }

   public String getDailyQuantity() {
      return DailyQuantity;
   }

   public void setDailyQuantity(String dailyQuantity) {
      DailyQuantity = dailyQuantity;
   }

   public String getTotalAmount() {
      return TotalAmount;
   }

   public void setTotalAmount(String totalAmount) {
      TotalAmount = totalAmount;
   }


}
