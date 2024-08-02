package com.inaxdevelopers.mydailymilk.adapter.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.inaxdevelopers.mydailymilk.databinding.ItemDailyMilkBinding;
import com.inaxdevelopers.mydailymilk.model.MilkModel;
import com.inaxdevelopers.mydailymilk.activites.DailyMilkDataEntry;

import java.util.List;

public class DailyMilkAddAdapter extends RecyclerView.Adapter<DailyMilkAddAdapter.ViewHolder> {
    Context context;
    List<MilkModel> milkModels;

    public DailyMilkAddAdapter(Context context, List<MilkModel> milkModels) {
        this.context = context;
        this.milkModels = milkModels;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDailyMilkBinding binding = ItemDailyMilkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyMilkAddAdapter.ViewHolder holder, int position) {
        MilkModel milkModel = milkModels.get(position);
        holder.bind(milkModel);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DailyMilkDataEntry.class);
            intent.putExtra("Data", milkModel);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return milkModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDailyMilkBinding binding;

        public ViewHolder(@NonNull ItemDailyMilkBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(MilkModel milkModel) {
            binding.setMilkModel(milkModel);
            binding.executePendingBindings();
            Glide.with(context)
                    .asBitmap()
                    .load(milkModel.getMilkImagePath())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            roundedDrawable.setCornerRadius(20f);
                            binding.showImage.setImageDrawable(roundedDrawable);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }
    }
}
