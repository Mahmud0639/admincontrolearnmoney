package com.manuni.earnmytakaadmincontrolappmanuni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityAllUserSendMessageBinding;

public class AllUserSendMessageActivity extends AppCompatActivity {

    ActivityAllUserSendMessageBinding binding;
    private String userId;
    String message;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUserSendMessageBinding.inflate(getLayoutInflater());
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
                        Toast.makeText(AllUserSendMessageActivity.this, "Message sent successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.deleteThisUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.collection("users").document(userId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AllUserSendMessageActivity.this, "User deleted successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}