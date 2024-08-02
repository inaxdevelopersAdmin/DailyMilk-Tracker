package com.inaxdevelopers.mydailymilk.adapter.customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inaxdevelopers.mydailymilk.databinding.ItemDailyMilksBinding;
import com.inaxdevelopers.mydailymilk.model.DailyAdd;

import java.util.List;

public class DailyDataAddCustomerMilkAdapter extends RecyclerView.Adapter<DailyDataAddCustomerMilkAdapter.ViewHolder> {
    Context context;
    List<DailyAdd> filteredList;

    public DailyDataAddCustomerMilkAdapter(Context context, List<DailyAdd> filteredList) {
        this.context = context;
        this.filteredList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DailyDataAddCustomerMilkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDailyMilksBinding binding = ItemDailyMilksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyDataAddCustomerMilkAdapter.ViewHolder holder, int position) {
        DailyAdd model = filteredList.get(position);
        holder.Bind(model);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDailyMilksBinding binding;

        public ViewHolder(@NonNull ItemDailyMilksBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void Bind(DailyAdd model) {
            binding.setDailyAdd(model);
            binding.executePendingBindings();
        }
    }
}
