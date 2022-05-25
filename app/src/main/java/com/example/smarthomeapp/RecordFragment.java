package com.example.smarthomeapp;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthomeapp.Module.DeviceModel;
import com.example.smarthomeapp.databinding.FragmentHomeBinding;
import com.example.smarthomeapp.databinding.FragmentRecordBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecordFragment extends Fragment {

    private FragmentRecordBinding binding;
    protected static final int RESULT_SPEECH = 1;
    private  List<DeviceModel> deviceModels;
    private FirebaseDatabase firebaseDatabase;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecordBinding.inflate(inflater,container,false);

        binding.btnSpeak.setOnClickListener(view1 -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                startActivityForResult(intent, RESULT_SPEECH);
                binding.tvText.setText("");
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });


        deviceModels = new ArrayList<>();
        firebaseDatabase.getInstance().getReference("livingroom")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot drinkSnapshot:snapshot.getChildren()){
                                DeviceModel deviceModel = drinkSnapshot.getValue(DeviceModel.class);
                                deviceModels.add(deviceModel);

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    String dataVoice = new String(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
                    for (int i = 0; i < deviceModels.size(); i++) {

                        if (deviceModels.get(i).getStyle().equals("button")) {
                            //+ deviceModels.get(i).getName()
                            if(dataVoice.contentEquals("Turn on "+ deviceModels.get(i).getName())||dataVoice.contentEquals("turn on "+ deviceModels.get(i).getName())){
                                deviceModels.get(i).status = true;
                                firebaseDatabase.getInstance().getReference("livingroom")
                                        .child(deviceModels.get(i).getId()).setValue(deviceModels.get(i));
                                Toast.makeText(requireContext(), "đã bật "+ deviceModels.get(i).getName(), Toast.LENGTH_LONG).show();
                            }
                            else if(dataVoice.contentEquals("Turn off "+ deviceModels.get(i).getName())||dataVoice.contentEquals("turn off "+ deviceModels.get(i).getName())){
                                deviceModels.get(i).status = false;
                                firebaseDatabase.getInstance().getReference("livingroom")
                                        .child(deviceModels.get(i).getId()).setValue(deviceModels.get(i));
                                Toast.makeText(requireContext(), "đã tắt "+ deviceModels.get(i).getName(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
                break;
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}