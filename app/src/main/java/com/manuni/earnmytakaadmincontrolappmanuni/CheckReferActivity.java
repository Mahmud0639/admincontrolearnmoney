package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityCheckReferBinding;

import java.util.ArrayList;

public class CheckReferActivity extends AppCompatActivity {
    ActivityCheckReferBinding binding;
    private String referCodeForCheck;
    FirebaseFirestore firestore;
    ArrayList<CheckReferModel> referModels;
    CheckReferAdapter checkReferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckReferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        referCodeForCheck = getIntent().getStringExtra("referCodeCheck");
        firestore.collection("users").whereEqualTo("referCode",referCodeForCheck)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                referModels = new ArrayList<>();
                for (DocumentSnapshot snapshot: queryDocumentSnapshots){
                  CheckReferModel checkReferModel =  snapshot.toObject(CheckReferModel.class);

                  referModels.add(checkReferModel);

                }
                checkReferAdapter = new CheckReferAdapter(CheckReferActivity.this,referModels);
                binding.checkReferRV.setHasFixedSize(true);
                binding.checkReferRV.setLayoutManager(new LinearLayoutManager(CheckReferActivity.this));
                binding.checkReferRV.setAdapter(checkReferAdapter);
            }
        });

    }
}