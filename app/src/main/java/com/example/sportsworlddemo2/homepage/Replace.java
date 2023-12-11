package com.example.sportsworlddemo2.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.replace.ReplaceAnkle;
import com.example.sportsworlddemo2.replace.ReplaceRope;

public class Replace extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replace);

        Button fitness = (Button) findViewById(R.id.button54);

        fitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Article_fit.class);
                startActivity(intent);


            }
        });

        Button food = (Button) findViewById(R.id.button53);

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Article_food.class);
                startActivity(intent);
            }
        });

        btn1 = findViewById(R.id.button42);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, ReplaceAnkle.class);
                startActivity(intent);
            }
        });

        btn2 = findViewById(R.id.button43);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, ReplaceAnkle.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.button44);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, ReplaceAnkle.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.button45);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, ReplaceAnkle.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.button62);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, ReplaceRope.class);
                startActivity(intent);
            }
        });

        ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5;

        Ibtn1 = findViewById(R.id.imageButton35);
        Ibtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Reservation1.class);
                startActivity(intent);
            }
        });

        Ibtn2 = findViewById(R.id.imageButton37);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(Replace.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton38);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Home.class);
                startActivity(intent);
            }
        });

        Ibtn4 = findViewById(R.id.imageButton39);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton40);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Replace.this, Article_food.class);
                startActivity(intent);
            }
        });
    }
}
