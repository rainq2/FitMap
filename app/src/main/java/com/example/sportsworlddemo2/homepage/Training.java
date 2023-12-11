package com.example.sportsworlddemo2.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_arms;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_back;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_chest;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_core;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_leg;
import com.example.sportsworlddemo2.homepage.trainingpage.Training_shoulder;

public class Training extends AppCompatActivity {
    private ImageButton arms,chest,back,leg,shoulder,core;
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training);

        Button sosButton = findViewById(R.id.button41);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 檢查權限是否已授予
                if (ContextCompat.checkSelfPermission(Training.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果未授予權限，則請求權限
                    ActivityCompat.requestPermissions(Training.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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

        arms = (ImageButton) findViewById(R.id.imageButton27);
        arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_arms.class);
                startActivity(intent);
            }
        });

        chest = (ImageButton) findViewById(R.id.imageButton8);
        chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_chest.class);
                startActivity(intent);
            }
        });
        back = (ImageButton) findViewById(R.id.imageButton11);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_back.class);
                startActivity(intent);
            }
        });
        shoulder = (ImageButton) findViewById(R.id.imageButton12);
        shoulder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_shoulder.class);
                startActivity(intent);
            }
        });
        leg = (ImageButton) findViewById(R.id.imageButton9);
        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_leg.class);
                startActivity(intent);
            }
        });
        core = (ImageButton) findViewById(R.id.imageButton10);
        core.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Training_core.class);
                startActivity(intent);
            }
        });


        btn1 = findViewById(R.id.imageButton13);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.imageButton14);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(Training.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.imageButton15);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Home.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.imageButton17);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training.this, Article_food.class);
                startActivity(intent);
            }
        });

        setting = (ImageButton) findViewById(R.id.imageButton7);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training.this,Setting.class);
                startActivity(intent);
            }
        });
    }


    // 接收權限請求的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果用戶授予權限，則執行打電話的程式碼
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:119"));
                startActivity(callIntent);
            }
        }
    }
}
