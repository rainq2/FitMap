package com.example.sportsworlddemo2.homepage.trainingpage;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.homepage.Article_food;
import com.example.sportsworlddemo2.homepage.Home;
import com.example.sportsworlddemo2.homepage.Replace;
import com.example.sportsworlddemo2.homepage.Reservation1;
import com.example.sportsworlddemo2.homepage.RunningDemo3;
import com.example.sportsworlddemo2.homepage.Training;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Training_back extends AppCompatActivity {
    TextView root_back_View;
    private Button btn_edit,btn_delete_back;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable fetchDataRunnable;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "my_channel";



    public void cal_Onclick(View view) {
        //0522
        Intent intent = new Intent();
        intent.setClass(Training_back.this, Training_backedit.class);
        startActivity(intent);
        //0522
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainging_back);

        root_back_View = findViewById(R.id.textView112);

        createNotificationChannel(); // 創建通知頻道

        fetchDataAndUpdate();

        btn_edit =findViewById(R.id.button90);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, Training_backedit.class);
                startActivity(intent);
            }
        });
        btn_delete_back=findViewById(R.id.button103);
        btn_delete_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, edit_back_menu.class);
                startActivity(intent);
            }
        });

        ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5;

        Ibtn1 = findViewById(R.id.imageButton70);
        Ibtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, Reservation1.class);
                startActivity(intent);
            }
        });

        Ibtn2 = findViewById(R.id.imageButton71);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(Training_back.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton72);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, Home.class);
                startActivity(intent);
            }
        });

        Ibtn4 = findViewById(R.id.imageButton74);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton75);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_back.this, Article_food.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(fetchDataRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(fetchDataRunnable);
    }
    private void fetchDataAndUpdate() {
        fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                new FetchDataTask().execute();
                handler.postDelayed(this, 3000); // 每隔三秒更新一次資料
            }
        };
        handler.post(fetchDataRunnable);
    }
    private class FetchDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> dataList = new ArrayList<>();
            try {
                Class.forName("com.mysql.jdbc.Driver"); // 載入 MySQL JDBC 驅動程式
                String databaseUrl = "jdbc:mysql://163.13.201.94:3306/sports?characterEncoding=UTF-8";
                Log.d("Database", "Connecting to database...");
                Connection connection = DriverManager.getConnection(databaseUrl, "sports", "00000000");
                Log.d("Database", "Connection successful");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM backMenu");

                // 提取數據並返回
                while (resultSet.next()) {
                    String action = resultSet.getString("action");
                    Log.d("Database", "Retrieved action: " + action);
                    dataList.add(action);
                }

                connection.close();
            } catch (Exception e) {
                Log.e("Database", "Error: " + e.toString());
                e.printStackTrace();
            }
            return dataList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (!result.isEmpty()) {
                StringBuilder dataText = new StringBuilder();
                for (String action : result) {
                    dataText.append(action).append("\n");
                }
                root_back_View.setText(dataText.toString());
                //showNotification("更新資料", "資料已更新"); // 顯示通知
            } else {
                root_back_View.setText("取得資料失敗");
            }
        }
    }
    //@RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification(String title, String message) {
        // 使用Snackbar顯示通知
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}
