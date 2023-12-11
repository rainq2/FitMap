package com.example.sportsworlddemo2.achievementpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

public class Achievement extends AppCompatActivity {
    private Button button, button3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);

        button = findViewById(R.id.button37);
        button3 = findViewById(R.id.button36);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Achievement.this, Exchange.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText("已完成");
                button.setBackgroundColor(Color.GRAY);
                button.setEnabled(false);

                TextView textView27 = findViewById(R.id.textView86);
                TextView textView25 = findViewById(R.id.textView84);
                int textView27Value = Integer.parseInt(textView27.getText().toString());
                int textView25Value = Integer.parseInt(textView25.getText().toString());
                int newTextView25Value = textView25Value + textView27Value;
                textView25.setText(String.valueOf(newTextView25Value));


            }
        });

    }
}
