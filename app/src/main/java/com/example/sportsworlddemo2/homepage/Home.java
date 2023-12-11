package com.example.sportsworlddemo2.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportsworlddemo2.R;


public class Home extends AppCompatActivity {
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;
    private Button button, button3;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        button = findViewById(R.id.button110);
        button3 = findViewById(R.id.button109);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Exchange.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("完成");
                button.setBackgroundColor(Color.GRAY);
                button.setEnabled(false);

                TextView textView27 = findViewById(R.id.textView148);
                TextView textView25 = findViewById(R.id.textView15);
                int textView27Value = Integer.parseInt(textView27.getText().toString());
                int textView25Value = Integer.parseInt(textView25.getText().toString());
                int newTextView25Value = textView25Value + textView27Value;
                textView25.setText(String.valueOf(newTextView25Value));


            }
        });

        Button article1 = (Button) findViewById(R.id.button2);
        article1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://ashleexiu.com/fitness-gender-difference/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button article2 = (Button) findViewById(R.id.button107);
        article2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://ashleexiu.com/posture-anxiety/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        Button article3 = (Button) findViewById(R.id.button108);
        article3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://ashleexiu.com/muscle-soreness-2/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });



        Button sosButton = findViewById(R.id.button30);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 檢查權限是否已授予
                if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果未授予權限，則請求權限
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    // 如果已授予權限，
                    //ACTION_CALL是馬上打
                    //ACTION_DIAL開啟撥號介面
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:119"));
                    startActivity(callIntent);
                }
            }
        });

        //取得按鍵

        btn1 = findViewById(R.id.imageView2);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.imageView3);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Home.this, Running.class);
                intent.setClass(Home.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.imageView5);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this, Training.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.imageView6);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Home.this, Article_food.class);
                startActivity(intent);
            }
        });

        setting = (ImageButton) findViewById(R.id.imageView);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Home.this, Setting.class);
                startActivity(intent);
            }
        });

    }
}
