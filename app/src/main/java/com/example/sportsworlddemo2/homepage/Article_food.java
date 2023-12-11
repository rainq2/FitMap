package com.example.sportsworlddemo2.homepage;

import android.Manifest;
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


public class Article_food extends AppCompatActivity {
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Food");
        setContentView(R.layout.article_food);


        Button sosButton = findViewById(R.id.button56);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 檢查權限是否已授予
                if (ContextCompat.checkSelfPermission(Article_food.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果未授予權限，則請求權限
                    ActivityCompat.requestPermissions(Article_food.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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

        Button fitness = (Button) findViewById(R.id.button17);
        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this, Article_fit.class);
                startActivity(intent);
            }
        });

        Button replace = (Button) findViewById(R.id.button51);

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this, Replace.class);
                startActivity(intent);


            }
        });

        Button food1 = (Button) findViewById(R.id.button18);
        //food1.setText("運動前中後吃什麼？營養師教你增肌減脂的運動飲食原則");
        food1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.commonhealth.com.tw/article/85944");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button food2 = (Button) findViewById(R.id.button19);

        //food2.setText("增肌減脂飲食原則公開，碳水蛋白質比例＋菜單食譜推薦");
        food2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.commonhealth.com.tw/article/88231");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button food3 = (Button) findViewById(R.id.button20);

        //food3.setText("阻力訓練後要吃多少蛋白質才能達到最好的增肌效果? 越多越好嗎? 營養師根據科學實證告訴你  ");
        food3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://reurl.cc/y7aa9q");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button food4 = (Button) findViewById(R.id.button21);

        //food4.setText("運動後除了蛋白質以外，一定要攝取碳水化合物嗎? 營養師根據科學實證告訴你");
        food4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://reurl.cc/7kqq1y");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button food5 = (Button) findViewById(R.id.button22);

        //food5.setText("想長肌肉，努力運動就夠了嗎? 吃對東西才能事半功倍3");
        food5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.foodnext.net/life/health2/paper/5357348292");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        btn1 = findViewById(R.id.imageButton19);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.imageButton20);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Article_food.this, Running.class);
                intent.setClass(Article_food.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.imageButton21);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this, Home.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.imageButton22);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this, Training.class);
                startActivity(intent);
            }
        });

        setting = (ImageButton) findViewById(R.id.imageButton18);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Article_food.this,Setting.class);
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
