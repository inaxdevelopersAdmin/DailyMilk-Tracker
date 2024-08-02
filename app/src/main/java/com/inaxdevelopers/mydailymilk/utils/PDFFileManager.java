package com.inaxdevelopers.mydailymilk.utils;

import java.io.File;
import java.util.ArrayList;

public class PDFFileManager {

    public static ArrayList<File> getAllPDFFiles(File directory) {
        ArrayList<File> pdfFiles = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    pdfFiles.addAll(getAllPDFFiles(file));
                } else {
                    if (file.getName().toLowerCase().endsWith(".pdf")) {
                        pdfFiles.add(file);
                    }
                }
            }
        }
        return pdfFiles;
    }

    public static void main(String[] args) {
        File rootDirectory = new File("/");
        ArrayList<File> pdfFiles = getAllPDFFiles(rootDirectory);
        for (File pdfFile : pdfFiles) {
            System.out.println(pdfFile.getAbsolutePath());
        }
    }

    public static boolean deletePDFFile(String filePath) {
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            boolean deleted = pdfFile.delete();
            return deleted;
        } else {
            return false;
        }
    }

}
