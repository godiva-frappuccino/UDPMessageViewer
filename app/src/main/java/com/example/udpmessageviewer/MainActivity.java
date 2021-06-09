package com.example.udpmessageviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UDPServer server = null;
        try {
            server = new UDPServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EditText portNumberInput = findViewById(R.id.editTextNumberSigned);
        Button enterButton = findViewById(R.id.enterButton);
        TextView IPAddressView = findViewById(R.id.IPAdressView);
        TextView portView = findViewById(R.id.portView);
        TextView toWrite = findViewById(R.id.messageView);
        toWrite.setTextSize(18.0f);

        try {
            String IPAddress = Utils.getIPAddress();
            IPAddressView.setText("IPAdress:" + IPAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UDPServer finalServer = server;
        enterButton.setOnClickListener(event -> {
            String portNumberString = portNumberInput.getText().toString();
            portView.setText("PORT:" + portNumberString);
            Integer portNumber = Integer.parseInt(portNumberString);
            try {
                finalServer.start(portNumber, toWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        finalServer.onMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            enterButton.setEnabled(false);
        });


    }


}