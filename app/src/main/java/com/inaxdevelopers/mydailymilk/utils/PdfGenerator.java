package com.inaxdevelopers.mydailymilk.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.util.Log;

import com.inaxdevelopers.mydailymilk.model.DailyAdd;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfGenerator {

    private static final String TAG = "PdfGenerator";
    private static String generatedFileNameMilk;
    private static String FinalAmountMilk;
    private static String generatedFileNameCustomer;
    private static String FinalAmountCustomer;

    public static void generatePdfFromRoomDataMilk(Context context, List<DailyMilkAdd> dataList, String customerName, String amount) {
        PdfDocument pdfDocument = new PdfDocument();
        int pageWidth = 600;
        int pageHeight = 800;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setTextSize(12);
        paint.setColor(android.graphics.Color.BLACK);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = dateFormat.format(new Date());
        canvas.drawText("Milk Name:" + customerName, 10, 20, paint);
        canvas.drawText("Date/Time: " + currentDateAndTime, 10, 40, paint);
        String[] headers = {"Date", "Milk Name", "Milk Price", "Quantity", "TotalAmount"};
        int startX = 10;
        int startY = 120;
        int cellWidth = pageWidth / headers.length;
        int maxRowsPerPage = 30;
        int rowsPrinted = 0;
        for (int i = 0; i < headers.length; i++) {
            float textWidth = paint.measureText(headers[i]);
            float xPos = startX + (cellWidth - textWidth) / 2 + (i * cellWidth);
            canvas.drawText(headers[i], xPos, startY, paint);
            canvas.drawRect(startX + (i * cellWidth), startY, startX + ((i + 1) * cellWidth), startY + 20, paint);
        }
        for (int i = 0; i < dataList.size(); i++) {
            DailyMilkAdd data = dataList.get(i);
            double totalAmountForCustomer = 0.0;
            if (customerName.equals(data.getDailyMilkName())) {
                double TotalAmountFinal = Double.parseDouble(data.getTotalAmount()) * Double.parseDouble(data.getDailyQuantity());
                totalAmountForCustomer += Math.abs(TotalAmountFinal);
                String sign = "+";
                if (TotalAmountFinal < 0) {
                    sign = "-";
                    TotalAmountFinal *= -1;
                }
                String FinalAmountMilk = sign + totalAmountForCustomer;
                String[] rowData = {
                        data.getDate(),
                        data.getDailyMilkName(),
                        data.getDailyMilkPrice(),
                        data.getDailyQuantity(),
                        String.valueOf(TotalAmountFinal)
                };
                for (int j = 0; j < rowData.length; j++) {
                    if (j < rowData.length) {
                        float textWidth = paint.measureText(rowData[j]);
                        float xPos = startX + (cellWidth - textWidth) / 2 + (j * cellWidth);
                        canvas.drawText(rowData[j], xPos, startY + ((rowsPrinted + 1) * 20), paint);
                        canvas.drawRect(startX + (j * cellWidth), startY + ((rowsPrinted + 1) * 20), startX + ((j + 1) * cellWidth), startY + ((rowsPrinted + 2) * 20), paint);
                    }
                }
                rowsPrinted++;

                if (rowsPrinted >= maxRowsPerPage) {
                    canvas.drawText("Total = " + FinalAmountMilk, 500, pageHeight - 20, paint);
                    pdfDocument.finishPage(page);
                    // Start a new page
                    page = pdfDocument.startPage(pageInfo);
                    canvas = page.getCanvas();
                    startY = 120;
                    rowsPrinted = 0;
                }
            }
        }
        canvas.drawText("Total = " + FinalAmountMilk, 500, pageHeight - 20, paint);
        pdfDocument.finishPage(page);
        try {
            String FileName = customerName + currentDateAndTime + ".pdf";
            generatePdfFromFileNameMilk(FileName);
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), FileName);
            pdfDocument.writeTo(new FileOutputStream(pdfFile));

        } catch (IOException e) {
            Log.e(TAG, "Error creating PDF", e);
        }
        pdfDocument.close();
    }

    public static String generatePdfFromFileNameMilk(String fileName) {
        generatedFileNameMilk = fileName;
        return fileName;
    }

    public static String getGeneratedFileNameMilk() {
        return generatedFileNameMilk;
    }


    public static void generatePdfFromRoomDataCustomer(Context context, List<DailyAdd> dataList, String customerName, String amount) {
        PdfDocument pdfDocument = new PdfDocument();
        int pageWidth = 600;
        int pageHeight = 800;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setTextSize(12);
        paint.setColor(android.graphics.Color.BLACK);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = dateFormat.format(new Date());
        canvas.drawText("Customer Name: " + customerName, 10, 20, paint);
        canvas.drawText("Date/Time: " + currentDateAndTime, 10, 40, paint);
        canvas.drawText("Customer amount: " + amount, 10, 60, paint);
        String[] headers = {"Date", "Milk Name", "Milk Price", "Quantity", "TotalAmount"};
        int startX = 10;
        int startY = 120;
        int cellWidth = pageWidth / headers.length;
        int maxRowsPerPage = 30;
        int rowsPrinted = 0;
        for (int i = 0; i < headers.length; i++) {
            float textWidth = paint.measureText(headers[i]);
            float xPos = startX + (cellWidth - textWidth) / 2 + (i * cellWidth);
            canvas.drawText(headers[i], xPos, startY, paint);
            canvas.drawRect(startX + (i * cellWidth), startY, startX + ((i + 1) * cellWidth), startY + 20, paint);
        }
        for (int i = 0; i < dataList.size(); i++) {
            DailyAdd data = dataList.get(i);
            double totalAmountForCustomer = 0.0;
            if (customerName.equals(data.getCustomerName())) {
                double TotalAmountFinal = Double.parseDouble(data.getMilkPrice()) * Double.parseDouble(data.getTodayMilk());
                totalAmountForCustomer += Math.abs(TotalAmountFinal);
                String sign = "+";
                if (TotalAmountFinal < 0) {
                    sign = "-";
                    TotalAmountFinal *= -1;
                }
                String FinalAmountCustomer = sign + totalAmountForCustomer;
                String[] rowData = {
                        data.getDate(),
                        data.getMilk(),
                        data.getMilkPrice(),
                        data.getTodayMilk(),
                        String.valueOf(TotalAmountFinal)
                };
                for (int j = 0; j < rowData.length; j++) {
                    if (j < rowData.length) {
                        float textWidth = paint.measureText(rowData[j]);
                        float xPos = startX + (cellWidth - textWidth) / 2 + (j * cellWidth);
                        canvas.drawText(rowData[j], xPos, startY + ((rowsPrinted + 1) * 20), paint);
                        canvas.drawRect(startX + (j * cellWidth), startY + ((rowsPrinted + 1) * 20), startX + ((j + 1) * cellWidth), startY + ((rowsPrinted + 2) * 20), paint);
                    }
                }
                rowsPrinted++;

                if (rowsPrinted >= maxRowsPerPage) {
                    canvas.drawText("Total = " + FinalAmountCustomer, 500, pageHeight - 20, paint);
                    pdfDocument.finishPage(page);
                    // Start a new page
                    page = pdfDocument.startPage(pageInfo);
                    canvas = page.getCanvas();
                    startY = 120;
                    rowsPrinted = 0;
                }
            }
        }
        canvas.drawText("Total = " + FinalAmountCustomer, 500, pageHeight - 20, paint);
        pdfDocument.finishPage(page);
        try {
            String FileName = customerName + currentDateAndTime + ".pdf";
            generatePdfFromFileNameCustomer(FileName);
            File pdfFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), FileName);
            pdfDocument.writeTo(new FileOutputStream(pdfFile));

        } catch (IOException e) {
            Log.e(TAG, "Error creating PDF", e);
        }
        pdfDocument.close();
    }

    public static String generatePdfFromFileNameCustomer(String fileName) {
        generatedFileNameCustomer = fileName;
        return fileName;
    }

    public static String getGeneratedFileNameCustomer() {
        return generatedFileNameCustomer;
    }
}
