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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Training_chest extends AppCompatActivity {
    TextView root_chest_View;
    private Button btn_edit,btn_delete_menu;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable fetchDataRunnable;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "my_channel";

    private int actionNumber = 1; // 動作編號



    public void cal_Onclick(View view) {
        //0522
        Intent intent = new Intent();
        intent.setClass(Training_chest.this, Training_chestedit.class);
        startActivity(intent);
        //0522
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_chest);

        root_chest_View = findViewById(R.id.textView125);

        createNotificationChannel(); // 創建通知頻道

        fetchDataAndUpdate();

        btn_edit =findViewById(R.id.button85);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, Training_chestedit.class);
                startActivity(intent);
            }
        });
        btn_delete_menu=findViewById(R.id.button11);
        btn_delete_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, edit_chest_menu.class);
                startActivity(intent);
            }
        });

        ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5;

        Ibtn1 = findViewById(R.id.imageButton80);
        Ibtn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, Reservation1.class);
                startActivity(intent);
            }
        });

        Ibtn2 = findViewById(R.id.imageButton81);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Training.this, Running.class);
                intent.setClass(Training_chest.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton82);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, Home.class);
                startActivity(intent);
            }
        });

        Ibtn4 = findViewById(R.id.imageButton83);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton84);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Training_chest.this, Article_food.class);
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
                new FetchDataAsyncTask().execute();
                handler.postDelayed(this, 3000); // 每隔三秒更新一次資料
            }
        };
        handler.post(fetchDataRunnable);
    }
    private class FetchDataAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://163.13.201.94/chest_getData.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);
                StringBuilder dataText = new StringBuilder();

                // 更改為照順序的第一個、第二個、以此類推
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String action = jsonObject.getString("action");
                    dataText.append(action).append("\n");
                }

                root_chest_View.setText(dataText.toString());
            } catch (JSONException e) {
                e.printStackTrace();
                root_chest_View.setText("您尚未擁有菜單");
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
