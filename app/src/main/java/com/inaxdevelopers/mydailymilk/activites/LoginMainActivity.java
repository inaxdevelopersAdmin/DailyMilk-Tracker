package com.inaxdevelopers.mydailymilk.activites;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.databinding.ActivityLoginMainBinding;
import com.inaxdevelopers.mydailymilk.utils.LocaleHelper;
import com.inaxdevelopers.mydailymilk.utils.SessionManager;

public class LoginMainActivity extends AppCompatActivity {

    ActivityLoginMainBinding binding;
    private SessionManager sessionManager;
    Context context;
    Resources resources;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(getApplicationContext());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.login.setOnClickListener(v -> {
            String username = binding.username.getText().toString();
            String password = binding.paswword.getText().toString();
            if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (username.equals("admin") && password.equals("admin123")) {
                sessionManager.setLoggedIn(true);
                sessionManager.setUserRole("admin");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (username.equals("user") && password.equals("user123")) {
                sessionManager.setLoggedIn(true);
                sessionManager.setUserRole("user");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, R.string.Invalid, Toast.LENGTH_SHORT).show();
            }
        });

        binding.gujarati.setOnClickListener(v -> {
            context = LocaleHelper.setLocale(this, "gu");
            resources = context.getResources();
        });
        binding.hindi.setOnClickListener(v -> {
            context = LocaleHelper.setLocale(this, "hi");
            resources = context.getResources();
        });
        binding.english.setOnClickListener(v -> {
            context = LocaleHelper.setLocale(this, "en");
            resources = context.getResources();

        });
    }
}