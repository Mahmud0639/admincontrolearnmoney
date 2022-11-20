package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityWithdrawBinding;

import java.util.ArrayList;

public class WithdrawActivity extends AppCompatActivity {
    ActivityWithdrawBinding binding;
    FirebaseFirestore database;
    ArrayList<WithdrawModel> list;
    WithdrawAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        database.collection("withdraws").orderBy("createdAt", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                 list = new ArrayList<>();
               for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                  WithdrawModel model = snapshot.toObject(WithdrawModel.class);
                  list.add(model);
               }
               adapter = new WithdrawAdapter(getApplicationContext(),list);
               binding.withdrawInfoRV.setHasFixedSize(true);
               binding.withdrawInfoRV.setLayoutManager(new LinearLayoutManager(WithdrawActivity.this));
               binding.withdrawInfoRV.setAdapter(adapter);
               adapter.notifyDataSetChanged();
            }
        });


    }
}