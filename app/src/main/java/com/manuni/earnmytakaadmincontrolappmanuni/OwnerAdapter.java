package com.manuni.earnmytakaadmincontrolappmanuni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.CheckReferSampleBinding;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.OwnerSampleBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerAdapterViewHolder> {
    Context context;
    ArrayList<CheckReferModel> checkReferModels;

    public OwnerAdapter(Context context, ArrayList<CheckReferModel> checkReferModels){
        this.context = context;
        this.checkReferModels = checkReferModels;
    }

    @NonNull
    @Override
    public OwnerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owner_sample,parent,false);
        return new OwnerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerAdapterViewHolder holder, int position) {
        CheckReferModel model = checkReferModels.get(position);
        holder.binding.referCodeRefer.setText(model.getReferCode());
        holder.binding.statusRefer.setText(model.getStatus());
        holder.binding.emailUserRefer.setText(model.getEmail());
        holder.binding.nameUserRefer.setText(model.getName());

        try {
            Glide.with(context).load(model.getProfile()).placeholder(R.drawable.ic_avatar).into(holder.binding.ownerImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DecimalFormat format = new DecimalFormat("#.##");
        holder.binding.coinsIdRefer.setText("TK "+String.valueOf(Double.valueOf( format.format(model.getCoins()))));
    }

    @Override
    public int getItemCount() {
        return checkReferModels.size();
    }

    public class OwnerAdapterViewHolder extends RecyclerView.ViewHolder {
        OwnerSampleBinding binding;

        public OwnerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = OwnerSampleBinding.bind(itemView);
        }
    }
}
