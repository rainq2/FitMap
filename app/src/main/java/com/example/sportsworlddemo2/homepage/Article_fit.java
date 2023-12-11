package com.example.sportsworlddemo2.homepage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;


public class Article_fit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Fitness");
        setContentView(R.layout.article_fit);

        Button food = (Button) findViewById(R.id.button23);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Article_food.class);
                startActivity(intent);
            }
        });

        Button replace = (Button) findViewById(R.id.button52);

        replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Replace.class);
                startActivity(intent);
            }
        });

        Button fit1 = (Button) findViewById(R.id.button25);

        //fit1.setText("健身新手該怎麼開始訓練?");
        fit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.peeta.tw/workout/workout-beginner/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button fit2 = (Button) findViewById(R.id.button26);

        //fit2.setText("健身前必讀攻略！6個初學健身項目教學、健身迷思導正、我該請健身教練嗎？");
        fit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.womenshealthmag.com/tw/fitness/work-outs/g39823093/workout-101/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button fit3 = (Button) findViewById(R.id.button27);

        //fit3.setText("【健身房注意事項】進入健身房之前，你必須要知道的10件大小事");
        fit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://thefashionmuscles.com/things-you-have-to-know-in-the-gym/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button fit4 = (Button) findViewById(R.id.button28);

        //fit4.setText("減低受傷機會、增強成效！必讀10個健身新手入門注意事項");
        fit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://reurl.cc/3xQQg0");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button fit5 = (Button) findViewById(R.id.button29);

        //fit5.setText("增肌卡關怎麼辦？ 醫：過度訓練恐釀成效打折");
        fit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://health.ltn.com.tw/article/breakingnews/4135400");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        ImageButton btn1,btn2,btn3,btn4,btn5;

        btn1 = findViewById(R.id.imageButton31);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.imageButton32);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(Article_fit.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.imageButton33);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Home.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.imageButton34);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Training.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.imageButton36);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Article_fit.this, Article_food.class);
                startActivity(intent);
            }
        });
    }
}
