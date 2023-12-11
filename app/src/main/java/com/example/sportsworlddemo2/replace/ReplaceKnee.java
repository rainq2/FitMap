package com.example.sportsworlddemo2.replace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.homepage.Article_food;
import com.example.sportsworlddemo2.homepage.Home;
import com.example.sportsworlddemo2.homepage.Replace;
import com.example.sportsworlddemo2.homepage.Reservation1;
import com.example.sportsworlddemo2.homepage.RunningDemo3;
import com.example.sportsworlddemo2.homepage.Training;


public class ReplaceKnee extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replace_knee);

        btn1 = findViewById(R.id.button60);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.cw.com.tw/article/5101009");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.button33);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://kknews.cc/zh-hk/health/8exgovq.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.button39);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.edh.tw/article/26141");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btn4 = findViewById(R.id.button40);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://reurl.cc/v794Wk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        btn5 = findViewById(R.id.button58);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sport.heho.com.tw/archives/165656");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5;

        Ibtn1 = findViewById(R.id.imageButton55);
        Ibtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceKnee.this, Reservation1.class);
                startActivity(intent);
            }
        });

        Ibtn2 = findViewById(R.id.imageButton56);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(ReplaceKnee.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton57);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceKnee.this, Home.class);
                startActivity(intent);
            }
        });

        Ibtn4 = findViewById(R.id.imageButton58);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceKnee.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton59);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceKnee.this, Article_food.class);
                startActivity(intent);
            }
        });
    }
}
