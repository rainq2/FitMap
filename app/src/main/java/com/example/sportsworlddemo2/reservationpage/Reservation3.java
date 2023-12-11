package com.example.sportsworlddemo2.reservationpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

public class Reservation3 extends AppCompatActivity {
    //設定輸入變數
    EditText e1,e2,e3,e4;
    TextView textViewResult;
    Button btn1,btn2;
    ImageButton imageButton;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation3);


        textViewResult = findViewById(R.id.textView131);
        //取得變數資料
        e1=(EditText)findViewById(R.id.textView25);  //器材
        e2=(EditText)findViewById(R.id.textView29);  //編號
        e3=(EditText)findViewById(R.id.textView31); //姓名
        e4=(EditText)findViewById(R.id.textView34); //學號
        //取得時間 & 日期
        Bundle bundle1 = getIntent().getExtras();
        String mspn = bundle1.getString("spinner");
        String mspn1 = bundle1.getString("spinner1");
        String date = bundle1.getString("日期");

        //取得按鍵
        btn1 = findViewById(R.id.button10);
        btn1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (e1.getText().toString().isEmpty() || e2.getText().toString().isEmpty() ||
                        e3.getText().toString().isEmpty() || e4.getText().toString().isEmpty()) {
                    textViewResult.setText("請填寫完整資訊");
                    // 使用者未輸入完整資訊，直接結束方法
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Reservation3.this, Reservation4.class);
                Bundle bundle = new Bundle();
                bundle.putString("器材",e1.getText().toString());
                bundle.putString("編號",e2.getText().toString());
                bundle.putString("姓名",e3.getText().toString());
                bundle.putString("學號",e4.getText().toString());
                bundle.putString("日期",date);
                bundle.putString("spinner",mspn);
                bundle.putString("spinner1",mspn1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn2 = findViewById(R.id.button9);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Reservation3.this, Reservation2.class);
                startActivity(intent);
            }
        });
    }
}
