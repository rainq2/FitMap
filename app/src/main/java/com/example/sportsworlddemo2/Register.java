package com.example.sportsworlddemo2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.database.RegisterDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
    EditText editUsername, editPassword, editPhone, editEmail,editHeight,editWeight;
    Button buttonRegister;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editUsername = findViewById(R.id.textView);
        editPassword = findViewById(R.id.textView2);
        editPhone = findViewById(R.id.textView3);
        editEmail = findViewById(R.id.textView7);
        editHeight = findViewById(R.id.textView8);
        editWeight = findViewById(R.id.textView9);

        buttonRegister = findViewById(R.id.button);
        textViewResult = findViewById(R.id.textView10);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                RegisterDB registerThread = new RegisterDB(editUsername, editPassword, editPhone, editEmail,
                        editHeight, editWeight, textViewResult, handler, Register.this, getApplicationContext());

                // Start the login thread
                Thread thread = new Thread(registerThread);
                thread.start();


            }
        });
    }
}
