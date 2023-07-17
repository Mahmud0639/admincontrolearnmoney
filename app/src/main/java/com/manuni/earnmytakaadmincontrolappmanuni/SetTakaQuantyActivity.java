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
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivitySetTakaQuantyBinding;

public class SetTakaQuantyActivity extends AppCompatActivity {
    ActivitySetTakaQuantyBinding binding;
    FirebaseFirestore firestore;
    String takaQuantity;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetTakaQuantyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setTitle("Quantity");
        dialog.setMessage("Adding Quantity...");

        binding.appCompatButtonSetTaka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                takaQuantity = binding.textInputLayoutSetTaka.getEditText().getText().toString().trim();
                firestore.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                            documentSnapshot.getReference().update("quantityTaka",takaQuantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Toast.makeText(SetTakaQuantyActivity.this, "Quantity updated.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

    }
}