package com.example.smarthomeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.firebase.auth.FirebaseAuth;
import android.net.Network;

import org.json.JSONObject;

public class DataToEsp extends AppCompatActivity {

   // TextView getIpAddress;
    //TextView getIdUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_to_esp);
        AndroidNetworking.initialize(getApplicationContext());
        //getIpAddress =(TextView) findViewById(R.id.getIPAddress);
        //getIdUser =(TextView) findViewById(R.id.getIdUser);
      //  firebaseAuth = FirebaseAuth.getInstance();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        //get ip address
       // getIpAddress.setText(ipAddress);
        // getid user firebase
        //getIdUser.setText(firebaseAuth.getUid());


        Button btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Send data via HTTP GET*/
                AndroidNetworking.get("http://192.168.4.1/get")
                        .addQueryParameter("data", "HelloWorld")
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(), anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        Button btnPost = findViewById(R.id.btn_post);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Send data via HTTP POST*/
                AndroidNetworking.post("https://{ipAddress}/123")
                        .addPathParameter("id","0")
                        .addStringBody(ipAddress)
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response + ipAddress, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(), anError.getErrorBody()+ipAddress, Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}


//
//    private FirebaseAuth firebaseAuth;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_data_to_esp);
//        getIpAddress =(TextView) findViewById(R.id.getIPAddress);
//        getIdUser =(TextView) findViewById(R.id.getIdUser);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
//        //get ip address
//        getIpAddress.setText(ipAddress);
//        // getid user firebase
//        getIdUser.setText(firebaseAuth.getUid());
//    }

