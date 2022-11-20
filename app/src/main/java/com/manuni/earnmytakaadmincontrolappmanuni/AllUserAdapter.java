package com.manuni.earnmytakaadmincontrolappmanuni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.AllUserSampleBinding;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder> {
    Context context;
    ArrayList<AllUserModel> list;

    public AllUserAdapter(Context context, ArrayList<AllUserModel> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_user_sample,parent,false);
        return new AllUserViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull AllUserViewHolder holder, int position) {
            AllUserModel model = list.get(position);
            holder.binding.referCodeRefer.setText("ReferCode: "+model.getReferCode());
            holder.binding.userId.setText("userId: "+model.getuId());
            holder.binding.emailUserRefer.setText("Email: "+model.getEmail());
            holder.binding.nameUserRefer.setText("Name: "+model.getName());
            holder.binding.statusRefer.setText("Status: "+model.getStatus());
            holder.binding.userCount.setText(String.format("%d",position+1));
            holder.binding.bKashNumber.setText("bKash: "+model.getbKashNumber());
        DecimalFormat format = new DecimalFormat("#.##");
        holder.binding.coinsIdRefer.setText("TK "+String.valueOf(Double.valueOf( format.format(model.getCoins()))));
        holder.binding.limitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                database.collection("users").document(model.getuId())
                        .update("status","limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.binding.statusRefer.setText("Status: limit");
                        Toast.makeText(context, model.getName()+" has a limit state.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.binding.cashSentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("users").document(model.getuId()).update("adminMessage","আপনাকে টাকা পাঠানো হয়েছে। আপনার ব্যালেন্স চেক করুন।")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, model.getName()+" has received his withdraw.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        holder.binding.sendMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(context,AllUserSendMessageActivity.class);
                messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                messageIntent.putExtra("userId",model.getuId());
                context.startActivity(messageIntent);
            }
        });

        holder.binding.returnMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReturn = new Intent(context,ReturnMoneyActivity.class);
                intentReturn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentReturn.putExtra("userKey",model.getuId());
                intentReturn.putExtra("coins",model.getCoins());
                context.startActivity(intentReturn);
            }
        });

        holder.binding.limitOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("users").document(model.getuId()).update("status","No limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        holder.binding.statusRefer.setText("Status: No limit");
                        Toast.makeText(context, model.getName()+" has now No limit state", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        try {
            Glide.with(context).load(model.getProfile()).placeholder(R.drawable.ic_avatar).into(holder.binding.ownerImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterListForUser(ArrayList<AllUserModel> filterList){
        list = filterList;
        notifyDataSetChanged();
    }

    public class AllUserViewHolder extends RecyclerView.ViewHolder {
        AllUserSampleBinding binding;

        public AllUserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AllUserSampleBinding.bind(itemView);
        }
    }
}
