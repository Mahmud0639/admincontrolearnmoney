package com.manuni.earnmytakaadmincontrolappmanuni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.CheckReferSampleBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckReferAdapter extends RecyclerView.Adapter<CheckReferAdapter.CheckReferViewHolder> {
    Context context;
    ArrayList<CheckReferModel> list;

    public CheckReferAdapter(Context context,ArrayList<CheckReferModel> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public CheckReferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_refer_sample,parent,false);
        return new CheckReferViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull CheckReferViewHolder holder, int position) {
        CheckReferModel model = list.get(position);

        holder.binding.nameUserRefer.setText(model.getName());
        holder.binding.emailUserRefer.setText(model.getEmail());
        holder.binding.statusRefer.setText(model.getStatus());
        holder.binding.referCodeRefer.setText(model.getReferCode());
        holder.binding.userCount.setText(String.format("%d",position+1));

        DecimalFormat format = new DecimalFormat("#.##");
        holder.binding.coinsIdRefer.setText("TK "+String.valueOf(Double.valueOf( format.format(model.getCoins()))));

       /* holder.binding.cashSentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("users").document()
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CheckReferViewHolder extends RecyclerView.ViewHolder {

        CheckReferSampleBinding binding;

        public CheckReferViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CheckReferSampleBinding.bind(itemView);
        }
    }
}
