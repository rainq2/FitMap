package com.example.sportsworlddemo2.reservationpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.Repair;
import com.example.sportsworlddemo2.database.ReservationDB;
import com.example.sportsworlddemo2.homepage.Reservation1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Reservation4 extends AppCompatActivity {
    Button btn1,btn2;
    EditText studentName,studentId,equipment1,eNo1,date1,time1;

    TextView textViewResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation4);

        //在onCreate方法中初始化textViewResult
        textViewResult = findViewById(R.id.textView58);

        //取得按鍵


        //輸入值
        Bundle bundle = getIntent().getExtras();
        String e1 = bundle.getString("姓名" );
        TextView result1 = (TextView)findViewById(R.id.textView41);
        result1.setText(e1);

        String e2 = bundle.getString("學號" );
        TextView result2 = (TextView)findViewById(R.id.textView44);
        result2.setText(e2);

        String e3 = bundle.getString("器材" );
        TextView result3 = (TextView)findViewById(R.id.textView47);
        result3.setText(e3);
        String e4 = bundle.getString("編號" );
        TextView result4 = (TextView)findViewById(R.id.textView50);
        result4.setText(e4);

        //0522
        String d1 = bundle.getString("日期" );
        TextView result5 = (TextView)findViewById(R.id.textView53);
        result5.setText(d1);
        //0522
        if (bundle != null) {
            String mSpn = bundle.getString("spinner");
            String mSpn1 = bundle.getString("spinner1");
            TextView result6 = (TextView)findViewById(R.id.textView56);
            result6.setText(mSpn + " ~ " + mSpn1);
        }

        studentName=findViewById(R.id.textView41);
        studentId = findViewById(R.id.textView44);
        equipment1 = findViewById(R.id.textView47);
        eNo1 = findViewById(R.id.textView50);
        date1 = findViewById(R.id.textView53);
        time1 = findViewById(R.id.textView56);

        btn2 = findViewById(R.id.button12);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sname = studentName.getText().toString().trim();
                String sId = studentId.getText().toString().trim();
                String equipment = equipment1.getText().toString().trim();
                String eNo = eNo1.getText().toString().trim();
                String date = date1.getText().toString().trim();
                String time = time1.getText().toString().trim();
                Handler handler = new Handler();
                ReservationDB reservationThread =
                        new ReservationDB(sname, sId, equipment, eNo, date, time, textViewResult, handler, Reservation4.this, getApplicationContext());
                Thread thread = new Thread(reservationThread);
                thread.start();

            }
        });

    }
}
