package com.example.sportsworlddemo2.reservationpage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.homepage.Reservation1;

import java.util.Calendar;

public class Reservation2 extends AppCompatActivity {
    private Spinner mSpn,mSpn1;
    private TextView tv;
    private String str;
    Button btn1,btn2;
    //顯示日期、時間
    TextView d1, textViewResult;//0522
    TextView textDate;
    //這個dialog的監聽物件(目前空)
    DatePickerDialog.OnDateSetListener pickerDialog;
    Calendar calendar = Calendar.getInstance();//用來做date


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation2);

        textViewResult = findViewById(R.id.textView77);
        //下拉式選單
        mSpn = findViewById(R.id.spn);
        mSpn.setOnItemSelectedListener(spnOnItemSelected);
        mSpn1 = findViewById(R.id.spn1);
        mSpn1.setOnItemSelectedListener(spnOnItemSelected);
        //彈出日曆
        textDate=findViewById(R.id.Nullabc);
        pickerDialog= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);//年
                calendar.set(Calendar.MONTH,month);//月(*注意：此處的月份從0~11*)
                calendar.set(Calendar.DATE,dayOfMonth);//日
                textDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);//使其月份+1顯示
            }
        };
        d1=findViewById(R.id.Nullabc);
        //取得按鍵
        btn1=findViewById(R.id.button8);
        btn1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (d1.getText().toString().isEmpty() ) {
                    textViewResult.setText("請填寫完整資訊");
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(Reservation2.this, Reservation3.class);
                Bundle bundle = new Bundle();
                bundle.putString("日期",d1.getText().toString());
                bundle.putString("spinner", mSpn.getSelectedItem().toString());
                bundle.putString("spinner1", mSpn1.getSelectedItem().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btn2 = findViewById(R.id.button7);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Reservation2.this, Reservation1.class);
                startActivity(intent);
            }
        });
    }
    //選取日曆資料
    public void datePicker(View v){
        //建立date的dialog
        DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                pickerDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    //下拉式選單選取
    private AdapterView.OnItemSelectedListener spnOnItemSelected = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
        {
            String msg = parent.getItemAtPosition(position).toString();
            setToast(msg);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {
            // TODO Auto-generated method stub
        }
    };
    //懸浮對話框
    private void setToast (String text){
        Toast.makeText(Reservation2.this, text, Toast.LENGTH_SHORT).show();
    }
}
