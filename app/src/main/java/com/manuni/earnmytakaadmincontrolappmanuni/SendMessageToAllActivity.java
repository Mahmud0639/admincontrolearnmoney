package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivitySendMessageToAllBinding;

public class SendMessageToAllActivity extends AppCompatActivity {
    ActivitySendMessageToAllBinding binding;
    String message;
    FirebaseFirestore database;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendMessageToAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(SendMessageToAllActivity.this);
        dialog.setTitle("Message");
        dialog.setMessage("Sending...");
        dialog.setCancelable(false);

        binding.appCompatButtonSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                message = binding.editTxt.getEditText().getText().toString().trim();
                database.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            documentSnapshot.getReference().update("adminMessage",message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(SendMessageToAllActivity.this, "All users have received this message.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}