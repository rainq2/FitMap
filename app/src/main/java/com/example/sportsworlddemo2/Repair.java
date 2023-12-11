package com.example.sportsworlddemo2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.database.RepairDB;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Repair extends AppCompatActivity {
    EditText editNo, editDescription;
    Button buttonSubmit;
    TextView textViewResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair);

        editNo = findViewById(R.id.textView114);
        editDescription = findViewById(R.id.textView115);
        buttonSubmit = findViewById(R.id.button46);
        textViewResult = findViewById(R.id.textView116);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Handler handler = new Handler();
                RepairDB repairThread = new RepairDB(editNo, editDescription, textViewResult, handler, Repair.this, getApplicationContext());

                Thread thread = new Thread(repairThread);
                thread.start();
            }
        });
    }
}
