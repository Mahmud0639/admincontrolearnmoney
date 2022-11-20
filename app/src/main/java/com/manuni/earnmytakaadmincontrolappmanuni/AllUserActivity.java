package com.manuni.earnmytakaadmincontrolappmanuni;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.manuni.earnmytakaadmincontrolappmanuni.databinding.ActivityAllUserBinding;

import java.util.ArrayList;

public class AllUserActivity extends AppCompatActivity {
    ActivityAllUserBinding binding;
    FirebaseFirestore firestore;
    ArrayList<AllUserModel> list;
    AllUserAdapter allUserAdapter;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchBar = findViewById(R.id.searchName);

        firestore = FirebaseFirestore.getInstance();


        firestore.collection("users").orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list = new ArrayList<>();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    AllUserModel model = documentSnapshot.toObject(AllUserModel.class);
                    list.add(model);
                }
                allUserAdapter = new AllUserAdapter(AllUserActivity.this,list);
                binding.allUserRV.setLayoutManager(new LinearLayoutManager(AllUserActivity.this));
                binding.allUserRV.setHasFixedSize(true);
                binding.allUserRV.setAdapter(allUserAdapter);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<AllUserModel> myList = new ArrayList<>();
        for (AllUserModel items: list){
            if (items.getName().toLowerCase().contains(text.toLowerCase())){
                myList.add(items);
            }
        }
        allUserAdapter.filterListForUser(myList);
    }
}