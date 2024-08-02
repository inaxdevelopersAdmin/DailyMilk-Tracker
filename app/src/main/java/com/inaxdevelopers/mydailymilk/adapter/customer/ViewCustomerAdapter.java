package com.inaxdevelopers.mydailymilk.adapter.customer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.inaxdevelopers.mydailymilk.databinding.ItemViewAllDataBinding;
import com.inaxdevelopers.mydailymilk.model.CustomerModel;
import com.inaxdevelopers.mydailymilk.activites.DailAddCustomer_MilkActivity;
import java.util.List;

public class ViewCustomerAdapter extends RecyclerView.Adapter<ViewCustomerAdapter.ViewHolder> {
    Context context;
    List<CustomerModel> customerModels;

    public ViewCustomerAdapter(Context context, List<CustomerModel> customerModels) {
        this.customerModels = customerModels;
        this.context = context;
        notifyDataSetChanged();
    }

    public void SearchCustomer(List<CustomerModel> customerModels) {
        this.customerModels = customerModels;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemViewAllDataBinding binding = ItemViewAllDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCustomerAdapter.ViewHolder holder, int position) {
        CustomerModel model = customerModels.get(position);
        holder.binding(model);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DailAddCustomer_MilkActivity.class);
            intent.putExtra("Data", model);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return customerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemViewAllDataBinding binding;

        public ViewHolder(@NonNull ItemViewAllDataBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void binding(CustomerModel model) {
            binding.setCustomerModel(model);
            binding.executePendingBindings();
        }
    }
}
