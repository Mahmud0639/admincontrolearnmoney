package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseFirestore firestore;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Limiting process...");

        firestore = FirebaseFirestore.getInstance();


        binding.withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,WithdrawActivity.class));
            }
        });

        binding.checkReferUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String referCodeForCheck = binding.referEditTxtForCheck.getText().toString().trim();
                Intent intentForCheckActivity = new Intent(MainActivity.this,CheckReferActivity.class);
                intentForCheckActivity.putExtra("referCodeCheck",referCodeForCheck);
                startActivity(intentForCheckActivity);
            }
        });

        binding.getAllUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getAllIntent = new Intent(MainActivity.this,AllUserActivity.class);
                startActivity(getAllIntent);
            }
        });

        binding.deleteFakeUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fakeRefer = binding.referEditTxtForCheck.getText().toString().trim();
                firestore.collection("users").whereEqualTo("referCode",fakeRefer).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            documentSnapshot.getReference().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(MainActivity.this, "All fake users deleted successfully!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        binding.ownerOfThisReferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ownerReferCode = binding.referEditTxtForCheck.getText().toString().trim();

                Intent intentOwnerRefer = new Intent(MainActivity.this,OwnerOfReferActivity.class);
                intentOwnerRefer.putExtra("ownerRefer",ownerReferCode);
                startActivity(intentOwnerRefer);

            }
        });

        binding.limitAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot: queryDocumentSnapshots){

                            snapshot.getReference().update("status","limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "All fields limit updated!", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }

                    }
                });

                firestore.collection("withdraws").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        dialog.show();
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            documentSnapshot.getReference().update("status","limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Withdraw users also limited.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
        });

        binding.limitRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot1: queryDocumentSnapshots){
                            documentSnapshot1.getReference().update("status","No limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Users status is No limit", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                firestore.collection("withdraws").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        dialog.show();
                        for (DocumentSnapshot documentSnapshot2: queryDocumentSnapshots){
                            documentSnapshot2.getReference().update("status","No limit").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Withdraw status is No limit.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }


        });
    }
}