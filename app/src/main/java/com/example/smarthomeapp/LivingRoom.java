package com.example.smarthomeapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomeapp.Apdater.ListDeviceApdater;
import com.example.smarthomeapp.Module.DeviceModel;
import com.example.smarthomeapp.databinding.ActivityLivingRoomBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LivingRoom extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ListDeviceApdater listDeviceApdater;
    ArrayList<DeviceModel> list;
    private String nameRoom;
    private ActivityLivingRoomBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLivingRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        nameRoom = getIntent().getStringExtra("nameRoom");

        binding.regBack.setOnClickListener(view -> {
            onBackPressed();
        });

        recyclerView  = findViewById(R.id.loadDeviceRecyclerView);
        database = FirebaseDatabase.getInstance().getReference(nameRoom);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);

        list = new ArrayList<>();

        listDeviceApdater = new ListDeviceApdater(LivingRoom.this, list);
        recyclerView.setAdapter(listDeviceApdater);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    DeviceModel deviceModel = dataSnapshot.getValue(DeviceModel.class);
                    list.add(deviceModel);
                }
                listDeviceApdater.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}