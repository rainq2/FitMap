package com.example.sportsworlddemo2.homepage;

import static java.lang.Thread.sleep;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportsworlddemo2.R;

public class Running extends AppCompatActivity {
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;


    int[] imgId = {R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4
            , R.drawable.s5, R.drawable.s6, R.drawable.s7, R.drawable.s8};
    Button btnPrev, btnNext;
    ImageView imageView;
    //圖片索引，第幾張圖片
    int p = 0;
    //總共有多少張
    int count = imgId.length;
    int s=0;

    @SuppressLint("HandlerLeak")
    private final Handler handlerSend = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            final TextView text_view = findViewById(R.id.text_view);
            final TextView text_view2 = findViewById(R.id.text_view2);
            super.handleMessage(msg);
            // 重写handleMessage方法
            String one = "1";
            super.handleMessage(msg);
            if (msg.what == 1) {
                new Thread(() -> {
                    MysqlCon con = new MysqlCon();
                    con.run();
                    final int data1 = Integer.parseInt(con.getData());
                    //final String data1 = con.getData();
                    Log.v("OKk", String.valueOf(data1));
                    text_view.post(() -> text_view.setText(String.valueOf(data1)));
                    s=s+data1;
                    text_view2.post(() -> text_view2.setText(String.valueOf(s)));

                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    boolean b = s >= 50;
                    if (b == true) {
                        Log.v("6699", String.valueOf(data1));
                        //張數+1表示後一張
                        p++;
                        //如果已經是最後一張，按下後顯示第一張
                        if (p == count) p = 0;
                        imageView.setImageResource(imgId[p]);
                        s = 0;
                    }

                }).start();
                Toast.makeText(getApplicationContext(), "页面刷新成功！", Toast.LENGTH_SHORT).show();
                // 再次使用handler发送信息
                handlerSend.sendEmptyMessageDelayed(1, 10000);
            }
        }
    };
    private ImageView iv;


    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.running);

        handlerSend.sendEmptyMessageDelayed(1, 5000);
        //final TextView text_view = (TextView) findViewById(R.id.text_view);
        imageView = findViewById(R.id.imageView);

        setting = (ImageButton) findViewById(R.id.app_icon7);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Running.this,Setting.class);
                startActivity(intent);
            }
        });

        //取得按鍵

        btn1 = findViewById(R.id.btn_reservation);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Running.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.btn_home);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Running.this, Home.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.btn_training);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Running.this, Training.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.btn_article);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Running.this, Article_food.class);
                startActivity(intent);
            }
        });


        Button sosButton = findViewById(R.id.h_emergency);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 檢查權限是否已授予
                if (ContextCompat.checkSelfPermission(Running.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果未授予權限，則請求權限
                    ActivityCompat.requestPermissions(Running.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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
