package com.inaxdevelopers.mydailymilk.fragment.product;

import static android.app.Activity.RESULT_OK;

import static com.google.common.reflect.Reflection.getPackageName;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.inaxdevelopers.mydailymilk.R;
import com.inaxdevelopers.mydailymilk.activites.MainActivity;
import com.inaxdevelopers.mydailymilk.databinding.FragmentAddProductBinding;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.viewmodel.MilkViewModel;

import java.util.Map;

public class AddProductFragment extends Fragment {
    FragmentAddProductBinding binding;
    String selectedImagePath = "";
    private static final int CAMERA_REQUEST = 1;
    MilkViewModel milkViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddProductBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        milkViewModel = new ViewModelProvider(this).get(MilkViewModel.class);
        AddMilkData();
    }

    private void AddMilkData() {
        binding.AddImage.setOnClickListener(v -> {
            checkPermission();
//            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
//            startActivityForResult(chooserIntent, CAMERA_REQUEST);
        });
        binding.AddMilk.setOnClickListener(v -> {
            String milkName = binding.addMilkName.getText().toString().trim();
            String milkQuantity = binding.addMilkQuantity.getText().toString().trim();
            String milkPrice = binding.addMilkPrice.getText().toString().trim();
            String path;
            path = selectedImagePath;
            if (!milkName.isEmpty() && !milkQuantity.isEmpty() && !milkPrice.isEmpty() && !path.isEmpty()) {
                AddMilk(milkName, milkQuantity, milkPrice, path);
                Toast.makeText(getActivity(), "Product Add successfully", Toast.LENGTH_SHORT).show();
                binding.showImage.setImageDrawable(getActivity().getDrawable(R.drawable.no_image));
                binding.addMilkName.setText("");
                binding.addMilkQuantity.setText("");
                binding.addMilkPrice.setText("");
            } else {
                Toast.makeText(getActivity(), "Please Enter Required Data", Toast.LENGTH_SHORT).show();
            }
        });

        binding.RemoveMilk.setOnClickListener(v -> {
            binding.showImage.setImageDrawable(getActivity().getDrawable(R.drawable.no_image));
            binding.addMilkName.setText("");
            binding.addMilkQuantity.setText("");
            binding.addMilkPrice.setText("");
        });
    }

    private void AddMilk(String milkName, String milkQuantity, String milkPrice, String path) {
        MilkModel model = new MilkModel();
        model.setMilkName(milkName);
        model.setMilkQuantity(milkQuantity);
        model.setMilkPrice(milkPrice);
        model.setMilkImagePath(path);
        milkViewModel.insertMilk(model);
        Toast.makeText(getActivity(), "Data Add Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CAMERA_REQUEST == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    Glide.with(this)
                            .asBitmap()
                            .load(selectedImageUri)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
                                    roundedDrawable.setCornerRadius(20f);
                                    binding.showImage.setImageDrawable(roundedDrawable);
                                    selectedImagePath = getPathFromUri(selectedImageUri);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "Image Not Supported", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED : ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                AddImage();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) : shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                requestPermissionLauncher.launch(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? new String[]{Manifest.permission.READ_MEDIA_IMAGES} : new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        }
    }

    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
        boolean allPermissionsGranted = true;
        for (Map.Entry<String, Boolean> entry : permissions.entrySet()) {
            String permission = entry.getKey();
            boolean isGranted = entry.getValue();
            if (!isGranted) {
                allPermissionsGranted = false;
                break;
            }
        }

        if (allPermissionsGranted) {
            AddImage();
        } else {
            Toast.makeText(getActivity(), "You need to allow all the required permissions", Toast.LENGTH_SHORT).show();
        }
    });

    private void AddImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CAMERA_REQUEST);

    }
}