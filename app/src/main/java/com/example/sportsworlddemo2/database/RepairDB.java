package com.example.sportsworlddemo2.database;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sportsworlddemo2.Login;
import com.example.sportsworlddemo2.Repair;
import com.example.sportsworlddemo2.homepage.Home;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RepairDB implements Runnable{

    EditText editNo, editDescription;
    TextView textViewResult;
    private Handler handler;
    private Context context;
    private Repair activity;

    public RepairDB(EditText editNo, EditText editDescription, TextView textViewResult,
                    Handler handler,  Repair activity, Context context) {
        this.editNo = editNo;
        this.editDescription = editDescription;
        this.textViewResult = textViewResult;
        this.handler = handler;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            String eNo = editNo.getText().toString().trim();
            String description = editDescription.getText().toString().trim();

            if (eNo.isEmpty() || description.isEmpty()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText("請填寫完整資訊");
                    }
                });
                // 使用者未輸入完整資訊，直接結束方法
                return;
            }

            URL url = new URL("http://163.13.201.94/repairdemo1.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();

            String data = "eNo=" + URLEncoder.encode(eNo, "UTF-8")
                    + "&description=" + URLEncoder.encode(description, "UTF-8")
                    ;

            connection.getOutputStream().write(data.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                String box = "";
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    box += line + "\n";
                }
                inputStream.close();
                final String result = box;


                //TODO id重複 無法傳輸
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText(result);
                        if (result.trim().equals("表單回應已收到！")) {
                            // Registration successful, switch to the login page after 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 建立一個 Intent 來切換到主頁面
                                    Intent intent = new Intent(context, Home.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            }, 3000);
                        }
                    }
                });
            } else {
                throw new Exception("HTTP response code: " + responseCode);
            }
        } catch (Exception e) {
            final String error = e.toString();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textViewResult.setText(error);
                }
            });
        }
    }
}
