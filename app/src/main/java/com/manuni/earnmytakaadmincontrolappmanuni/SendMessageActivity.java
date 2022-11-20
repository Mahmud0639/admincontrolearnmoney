package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivitySendMessageBinding;

public class SendMessageActivity extends AppCompatActivity {
    ActivitySendMessageBinding binding;
    String message;
    FirebaseFirestore database;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra("userId");

        binding.appCompatButtonSentMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = binding.editTxt.getEditText().getText().toString().trim();
                database.collection("users").document(userId).update("adminMessage",message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SendMessageActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}