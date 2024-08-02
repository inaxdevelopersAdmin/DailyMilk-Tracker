package com.inaxdevelopers.mydailymilk;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

public class MyApp extends Application {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
