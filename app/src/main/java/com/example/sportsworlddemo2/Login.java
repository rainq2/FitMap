package com.example.sportsworlddemo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.database.LoginDB;
import com.example.sportsworlddemo2.homepage.Home;
import com.example.sportsworlddemo2.homepage.Running;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Login extends AppCompatActivity {

    EditText editUsername, editPassword;
    Button buttonLogin;
    TextView textViewResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button buttonRegister = findViewById(R.id.button4);

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(Login.this, Register.class);
            startActivity(intent);
        });


        editUsername = findViewById(R.id.textView4);
        editPassword = findViewById(R.id.textView5);
        buttonLogin = findViewById(R.id.button3);
        textViewResult = findViewById(R.id.textView6);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();

                // Create a LoginThread instance
                LoginDB loginThread = new LoginDB(editUsername, editPassword, textViewResult, handler, Login.this, getApplicationContext());

                // Start the login thread
                Thread thread = new Thread(loginThread);
                thread.start();


            }
        });
    }
}
