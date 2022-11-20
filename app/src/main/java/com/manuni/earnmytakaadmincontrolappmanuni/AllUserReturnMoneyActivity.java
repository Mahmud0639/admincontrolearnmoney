package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityAllUserReturnMoneyBinding;

public class AllUserReturnMoneyActivity extends AppCompatActivity {
    ActivityAllUserReturnMoneyBinding binding;
    String amount,userKey;
    FirebaseFirestore firestore;
    Double returnMoney;
    AllUserModel allUserModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUserReturnMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        userKey = getIntent().getStringExtra("userKey");
        binding.appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = binding.textInputLayout.getEditText().getText().toString().trim();
                returnMoney =  Double.valueOf(amount);
                binding.textInputLayout.getEditText().setText("");

                firestore.collection("users")
                        .document(userKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allUserModel = documentSnapshot.toObject(AllUserModel.class);
                        firestore.collection("users")
                                .document(userKey).update("coins",allUserModel.getCoins()-returnMoney).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AllUserReturnMoneyActivity.this, "Coins have been subtracted.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


            }
        });

        binding.appCompatButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = binding.textInputLayout.getEditText().getText().toString().trim();
                returnMoney =  Double.valueOf(amount);
                binding.textInputLayout.getEditText().setText("");

                firestore.collection("users")
                        .document(userKey).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        allUserModel = documentSnapshot.toObject(AllUserModel.class);
                        firestore.collection("users")
                                .document(userKey).update("coins", FieldValue.increment(returnMoney)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AllUserReturnMoneyActivity.this, "Coins have been added.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}