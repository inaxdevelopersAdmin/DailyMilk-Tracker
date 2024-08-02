package com.inaxdevelopers.mydailymilk.adapter.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inaxdevelopers.mydailymilk.databinding.ItemDailyBinding;
import com.inaxdevelopers.mydailymilk.model.DailyMilkAdd;

import java.util.List;

public class DailyDataAdapter extends RecyclerView.Adapter<DailyDataAdapter.ViewHolder> {
    Context context;
    List<DailyMilkAdd> dailyAdds;


    public DailyDataAdapter(Context context, List<DailyMilkAdd> dailyAdds) {
        this.dailyAdds = dailyAdds;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDailyBinding binding = ItemDailyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyMilkAdd add = dailyAdds.get(position);
        holder.bind(add);
    }

    @Override
    public int getItemCount() {
        return dailyAdds.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDailyBinding binding;

        public ViewHolder(@NonNull ItemDailyBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(DailyMilkAdd add) {
            binding.setDailyMilkAdd(add);
            binding.executePendingBindings();
        }
    }
}
