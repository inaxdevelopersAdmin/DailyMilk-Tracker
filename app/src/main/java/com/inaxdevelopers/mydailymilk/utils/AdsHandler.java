package com.inaxdevelopers.mydailymilk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AdsHandler {
    public static AdsHandler instance;
    public static String bannerId = "ca-app-pub-3940256099942544/9214589741";
    public static String nativeId = "";
    public static String interstitialId = "ca-app-pub-3940256099942544/1033173712";
    public static String rewardedId = "";
    public static String openAds = "";
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    private static Activity activity;

    public static void setAdsOn(boolean switchAds) {
        editor.putBoolean("ads", switchAds);
        editor.apply();
    }

    public static boolean isAdsOn() {
        return sharedPreferences != null && sharedPreferences.getBoolean("ads", true);
    }


    public static synchronized AdsHandler getInstance(Activity activity) {
        AdsHandler.activity = activity;
        sharedPreferences = activity.getSharedPreferences("AdmobPref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (instance == null) {
            instance = new AdsHandler();
        }
        return instance;
    }
}
