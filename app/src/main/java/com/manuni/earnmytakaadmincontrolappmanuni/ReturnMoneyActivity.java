package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityReturnMoneyBinding;

public class ReturnMoneyActivity extends AppCompatActivity {
    ActivityReturnMoneyBinding binding;
    String amount,userKey;
    FirebaseFirestore firestore;
    Double returnMoney;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReturnMoneyBinding.inflate(getLayoutInflater());
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
                        user = documentSnapshot.toObject(User.class);
                        firestore.collection("users")
                                .document(userKey).update("coins",user.getCoins()-returnMoney).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ReturnMoneyActivity.this, "Coins have been subtracted.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


               /* firestore.collection("users").document(userKey).update("coins",returnMoney)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ReturnMoneyActivity.this, "Taka returned successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });*/
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
                        user = documentSnapshot.toObject(User.class);
                        firestore.collection("users")
                                .document(userKey).update("coins", FieldValue.increment(returnMoney)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ReturnMoneyActivity.this, "Coins have been added.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


    }

}