package com.example.sportsworlddemo2.homepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.Login;
import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.Repair;
import com.example.sportsworlddemo2.homepage.settingpage.Personal;

public class Setting extends AppCompatActivity {
    private Button button1,button2,button3,button4,button5,button15;
    ImageButton btn1,btn2,btn3,btn4,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        button2 = findViewById(R.id.button32);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Personal.class);

                startActivity(intent);
            }
        });


        button3 = findViewById(R.id.button35);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("確認登出");
                builder.setMessage("確定要登出吗？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击了“是”，显示成功登出提示，然后跳转到登录页面
                        Toast.makeText(Setting.this, "成功登出", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Setting.this, Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击了“否”，关闭对话框
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        button4 = findViewById(R.id.button34);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Repair.class);
                startActivity(intent);
            }
        });

    }
}
