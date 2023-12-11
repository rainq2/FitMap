package com.example.sportsworlddemo2.replace;

import android.content.Intent;
import android.content.pm.PackageManager;
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

public class ReplaceElbow extends AppCompatActivity {
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Elbow");
        setContentView(R.layout.replace_elbow);



        Button elbow1 = (Button) findViewById(R.id.button71);
        //elbow article 1 ("網球肘的8種好運動");
        elbow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.vondt.net/zh-TW/8-gode-ovelser-mot-tennisalbue/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button elbow2 = (Button) findViewById(R.id.button72);

        //elbow article 2 ("
        //【肩痛全攻略】常見肩痛原因、避免肩痛的習慣、超有效緩解肩痛運動。");
        elbow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.vogue.com.tw/beauty/article/%E8%82%A9%E7%97%9B%E7%B7%A9%E8%A7%A3");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button elbow3 = (Button) findViewById(R.id.button73);

        //elbow article 3"改善容易日積月累造成肩關節傷害的13個重訓動作");
        elbow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.sportsplanetmag.com/article/desc/220006891");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button elbow4 = (Button) findViewById(R.id.button74);

        //elbow article 4 ("
        //肩膀的復健運動，有三個細節要留意");
        elbow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.ezcareptc.tw/page/news/show.aspx?num=1056&page=3");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        Button elbow5 = (Button) findViewById(R.id.button75);

        //elbow article 5 (" 「肩頸痠痛」7大原因，教練給你10款肌力、放鬆訓練改善肩頸痠痛");
        elbow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.womenshealthmag.com/tw/fitness/work-outs/g36583028/shoulder-and-neck-pain/1");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);



            }
        });

        ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5;

        Ibtn1 = findViewById(R.id.imageButton50);
        Ibtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceElbow.this, Reservation1.class);
                startActivity(intent);
            }
        });

        Ibtn2 = findViewById(R.id.imageButton51);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(ReplaceElbow.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton52);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceElbow.this, Home.class);
                startActivity(intent);
            }
        });

        Ibtn4 = findViewById(R.id.imageButton53);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceElbow.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton54);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ReplaceElbow.this, Article_food.class);
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
