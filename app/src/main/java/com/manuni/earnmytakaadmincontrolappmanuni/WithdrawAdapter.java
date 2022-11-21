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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.WithdrawSampleBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.WithdrawViewHolder> {
    Context context;
    ArrayList<WithdrawModel> list;

    public WithdrawAdapter(Context context, ArrayList<WithdrawModel> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public WithdrawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.withdraw_sample,null);
        return new WithdrawViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull WithdrawViewHolder holder, int position) {
        WithdrawModel model = list.get(position);

        holder.binding.dateRequestUser.setText(String.valueOf(model.getCreatedAt()));
        holder.binding.nameUser.setText(model.getSentBy());
        holder.binding.referCodeUser.setText(model.getUserId());
        holder.binding.emailUser.setText(model.getuserEmail());
        holder.binding.paypalUser.setText(model.getpayPalEmail());
        holder.binding.bKashUser.setText(model.getMobile());
        holder.binding.cashOutUser.setText(model.getMyCoins());
        holder.binding.status.setText("Status: "+model.getStatus());
        holder.binding.withdrawUserCount.setText(String.format("%d",position+1));

        holder.binding.cashSentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("users").document(model.getUserId())
                        .update("adminMessage","আপনাকে টাকা পাঠানো হয়েছে। আপনার ব্যালেন্স চেক করুন।").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Withdraw has been done successfully!", Toast.LENGTH_SHORT).show();
                        firestore.collection("withdraws").document(model.getUserId()).update("userEmail",model.getuserEmail()+"(paid)")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Paid updated.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }


                });
            }

        });

        holder.binding.sendMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageIntent = new Intent(context,SendMessageActivity.class);
                messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                messageIntent.putExtra("userId",model.getUserId());
                context.startActivity(messageIntent);

            }
        });

        holder.binding.returnMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReturn = new Intent(context,ReturnMoneyActivity.class);
                intentReturn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentReturn.putExtra("userKey",model.getUserId());
                intentReturn.putExtra("coins",model.getMyCoins());
                context.startActivity(intentReturn);
            }
        });

        holder.binding.limitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore storageFire = FirebaseFirestore.getInstance();
                storageFire.collection("users").document(model.getUserId())
                        .update("status","limit")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(Void unused) {
                                holder.binding.status.setText("Status: limit");
                                storageFire.collection("withdraws").document(model.getUserId())
                                        .update("status","limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Status limit done!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(context, "Status limit done!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.binding.limitOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore storageFire = FirebaseFirestore.getInstance();
                storageFire.collection("users").document(model.getUserId())
                        .update("status","No Limit")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(Void unused) {
                                holder.binding.status.setText("Status: No limit");
                                storageFire.collection("withdraws").document(model.getUserId())
                                        .update("status","No limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Status No limit done!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(context, "Status limit off!", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void filterListForUser(ArrayList<WithdrawModel> filterList){
        list = filterList;
        notifyDataSetChanged();
    }

    public class WithdrawViewHolder extends RecyclerView.ViewHolder {
        WithdrawSampleBinding binding;

        public WithdrawViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = WithdrawSampleBinding.bind(itemView);
        }
    }
}
