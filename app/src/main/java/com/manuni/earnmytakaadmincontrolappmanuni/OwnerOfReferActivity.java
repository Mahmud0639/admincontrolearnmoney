package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityOwnerOfReferBinding;

import java.util.ArrayList;

public class OwnerOfReferActivity extends AppCompatActivity {
    ActivityOwnerOfReferBinding binding;
    FirebaseFirestore firestore;
    private String ownerReferCode;
    ArrayList<CheckReferModel> checkReferModels;
    OwnerAdapter ownerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOwnerOfReferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        ownerReferCode = getIntent().getStringExtra("ownerRefer");


        firestore.collection("users").document(ownerReferCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                checkReferModels = new ArrayList<>();
               CheckReferModel checkReferModel = documentSnapshot.toObject(CheckReferModel.class);
               checkReferModels.add(checkReferModel);

               ownerAdapter = new OwnerAdapter(OwnerOfReferActivity.this,checkReferModels);
               binding.ownerRV.setHasFixedSize(true);
               binding.ownerRV.setLayoutManager(new LinearLayoutManager(OwnerOfReferActivity.this));
               binding.ownerRV.setAdapter(ownerAdapter);
            }


        });

    }
}