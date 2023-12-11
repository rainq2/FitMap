package com.example.sportsworlddemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button nextPageStart = findViewById(R.id.button1);
        nextPageStart.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Login.class);
            startActivity(intent);
        });
    }
}