package com.android.sistemkontrolpakansapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.sistemkontrolpakansapi.databinding.ActivityFeedWeightScaleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedWeightScaleActivity extends AppCompatActivity {
    private ActivityFeedWeightScaleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedWeightScaleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("logs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FeedWeightScaleModel> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.add(new FeedWeightScaleModel(
                            dataSnapshot.child("day").getValue().toString(),
                            dataSnapshot.child("timestamp").getValue().toString(),
                            dataSnapshot.child("weight").getValue().toString()
                    ));
                }
                binding.recyclerView.setAdapter(new FeedWeightScaleAdapter(list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}