package com.android.sistemkontrolpakansapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.sistemkontrolpakansapi.databinding.ActivityMainBinding;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SimpleDateFormat simpleDateFormat;
    private DateFormat dateFormat;
    private String myFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myFormat = "dd-MM-yyyy HH:mm"; //In which you need put here
        simpleDateFormat = new SimpleDateFormat(myFormat, new Locale("id", "ID"));
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String moisture = snapshot.child("moisture").getValue().toString();
                String timestamp = snapshot.child("timestamp").getValue().toString();
                String turbidity = snapshot.child("turbidity").getValue().toString();
                String auto = snapshot.child("auto").getValue().toString();
                String waterIn = snapshot.child("water_in").getValue().toString();
                String waterOut = snapshot.child("water_out").getValue().toString();
                String heater = snapshot.child("heater").getValue().toString();
                String fan = snapshot.child("fan").getValue().toString();

                binding.textViewFeedMoisture.setText(moisture + "%");
                binding.textViewTurbidityWater.setText(turbidity);
                binding.textViewDateFeedMoisture.setText(formatTanggal(timestamp));
                binding.textViewDateTurbidityWaterMoisture.setText(formatTanggal(timestamp));

                if (auto.equalsIgnoreCase("0")) {
                    binding.labelSwitchModeOtomatis.setOn(false);
                    binding.labelSwitchWaterIn.setEnabled(true);
                    binding.labelSwitchWaterOut.setEnabled(true);
                    binding.labelSwitchHeater.setEnabled(true);
                    binding.labelSwitchFan.setEnabled(true);
                } else {
                    binding.labelSwitchModeOtomatis.setOn(true);
                    binding.labelSwitchWaterIn.setEnabled(false);
                    binding.labelSwitchWaterOut.setEnabled(false);
                    binding.labelSwitchHeater.setEnabled(false);
                    binding.labelSwitchFan.setEnabled(false);
                }

                if (waterIn.equalsIgnoreCase("0")) {
                    binding.labelSwitchWaterIn.setOn(false);
                } else {
                    binding.labelSwitchWaterIn.setOn(true);
                }

                if (waterOut.equalsIgnoreCase("0")) {
                    binding.labelSwitchWaterOut.setOn(false);
                } else {
                    binding.labelSwitchWaterOut.setOn(true);
                }

                if (heater.equalsIgnoreCase("0")) {
                    binding.labelSwitchHeater.setOn(false);
                } else {
                    binding.labelSwitchHeater.setOn(true);
                }

                if (fan.equalsIgnoreCase("0")) {
                    binding.labelSwitchFan.setOn(false);
                } else {
                    binding.labelSwitchFan.setOn(true);
                }

                for (DataSnapshot dataSnapshot : snapshot.child("logs").getChildren()) {
                    String date = dataSnapshot.child("timestamp").getValue().toString();
                    String weight = dataSnapshot.child("weight").getValue().toString();

                    binding.textViewDateFeedWeightScaleAverage.setText(formatTanggal(date));
                    binding.textViewFeedWeightScaleAverage.setText(weight + " Kg");
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.materialCardViewFeedWeightScaleAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FeedWeightScaleActivity.class));
            }
        });

        binding.labelSwitchModeOtomatis.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                binding.labelSwitchWaterIn.setEnabled(!isOn);
                binding.labelSwitchWaterOut.setEnabled(!isOn);
                binding.labelSwitchHeater.setEnabled(!isOn);
                binding.labelSwitchFan.setEnabled(!isOn);
                if (isOn) {
                    databaseReference.child("auto").setValue(1);
                } else {
                    databaseReference.child("auto").setValue(0);
                }
            }
        });

        binding.labelSwitchWaterIn.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    databaseReference.child("water_in").setValue(1);
                } else {
                    databaseReference.child("water_in").setValue(0);
                }
            }
        });

        binding.labelSwitchWaterOut.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    databaseReference.child("water_out").setValue(1);
                } else {
                    databaseReference.child("water_out").setValue(0);
                }
            }
        });

        binding.labelSwitchHeater.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    databaseReference.child("heater").setValue(1);
                } else {
                    databaseReference.child("heater").setValue(0);
                }
            }
        });

        binding.labelSwitchFan.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn) {
                    databaseReference.child("fan").setValue(1);
                } else {
                    databaseReference.child("fan").setValue(0);
                }
            }
        });
    }

    public String formatTanggal(String s) {
        String tanggal = "";
        try {
            Date date = dateFormat.parse(s);
            tanggal = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tanggal;
    }
}