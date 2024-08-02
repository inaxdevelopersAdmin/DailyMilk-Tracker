package com.inaxdevelopers.mydailymilk.activites;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.databinding.ActivityMainBinding;
import com.inaxdevelopers.mydailymilk.fragment.home.HomeFragment;
import com.inaxdevelopers.mydailymilk.fragment.home.PDFFragment;
import com.inaxdevelopers.mydailymilk.fragment.home.ProductFragment;
import com.inaxdevelopers.mydailymilk.fragment.home.UserFragment;
import com.inaxdevelopers.mydailymilk.utils.Ads;
import com.inaxdevelopers.mydailymilk.utils.SessionManager;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private SessionManager sessionManager;
    Fragment selectedFragment = null;
    MilkViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        Set_Toolbar();
        sessionManager = new SessionManager(getApplicationContext());
        SetBottomNavigation();
        Ads.getInstance().BannerAd(binding.adsViewLayout, this);
    }

    private void Set_Toolbar() {
        binding.toolbar.setTitle(R.string.app_name);
        binding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private void SetBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        binding.bottomNavigation.setSelectedItemId(R.id.main);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.customer) {
                selectedFragment = new UserFragment();
            } else if (itemId == R.id.main) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.Product) {
                selectedFragment = new ProductFragment();
            } else if (itemId == R.id.pdf) {
                selectedFragment = new PDFFragment();
            } else {
                selectedFragment = new HomeFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_code)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sessionManager.setLoggedIn(false);
                        finish();
                        finishAffinity();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton(R.string.rate_app, (dialog, which) -> {
                    String packageName = getPackageName();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    } catch (android.content.ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                    }
                    dialog.dismiss();
                }).show();
    }
}
