package com.inaxdevelopers.mydailymilk.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.inaxdevelopers.mydailymilk.R;

public class Ads {
    static Ads instance;
    private CustomAdsListener listener;
    Dialog dialog;

    public static boolean checkConnection(Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public void BannerAd(final FrameLayout Ad_Layout, Activity activity) {
        AdView mAdView = new AdView(activity);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(AdsHandler.bannerId);
        AdRequest adore = new AdRequest.Builder().build();
        mAdView.loadAd(adore);
        Ad_Layout.addView(mAdView);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Ad_Layout.setVisibility(View.VISIBLE);
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                Ad_Layout.setVisibility(View.GONE);

            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mAdView.destroy();
                Ad_Layout.setVisibility(View.GONE);
            }
        });
    }


    public void showCounterInterstitialAd(Activity activity, CustomAdsListener customAdsListener) {
        this.listener = customAdsListener;
        if (AdsHandler.sharedPreferences == null) {
            AdsHandler.getInstance(activity);
        }
        if (checkConnection(activity) && AdsHandler.isAdsOn()) {
            showLoading(activity, false);
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity, AdsHandler.interstitialId, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            listener.onFinish();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            listener.onFinish();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
                    hideLoading();
                    interstitialAd.show(activity);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Toast.makeText(activity, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                    hideLoading();
                    listener.onFinish();
                }
            });
        } else {
            listener.onFinish();
        }

    }

    public void showLoading(Activity activity, boolean cancelable) {
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(cancelable);
        if (!dialog.isShowing() && !activity.isFinishing()) {
            dialog.show();
        }
    }

    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    public static Ads getInstance() {
        if (instance == null) {
            synchronized (Ads.class) {
                if (instance == null)
                    instance = new Ads();
            }
        }
        return instance;
    }

}
