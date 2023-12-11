package com.example.sportsworlddemo2.database;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.example.sportsworlddemo2.Login;
import com.example.sportsworlddemo2.homepage.Home;
import com.example.sportsworlddemo2.homepage.Running;

public class LoginDB implements Runnable{
    private EditText editUsername;
    private EditText editPassword;
    private TextView textViewResult;
    private Handler handler;
    private Context context;
    private Login activity;


    public LoginDB(EditText editUsername, EditText editPassword, TextView textViewResult, Handler handler, Login activity, Context context) {
        this.editUsername = editUsername;
        this.editPassword = editPassword;
        this.textViewResult = textViewResult;
        this.handler = handler;
        this.activity=activity;
        this.context = context;

    }


    @Override
    public void run() {
        try {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText("請填寫完整資訊");
                    }
                });
                // 使用者未輸入完整資訊，直接結束方法
                return;
            }


            // Connect to the login PHP script
            URL url = new URL("http://163.13.201.94/logindemo1.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 設定CORS頭部，允許來自任何網域的請求
            connection.setRequestProperty("Access-Control-Allow-Origin", "*");
            connection.setRequestProperty("Access-Control-Allow-Methods", "*");
            connection.setRequestProperty("Access-Control-Allow-Headers", "Origin, Methods, Content-Type, Authorization");



            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();

            // Prepare the login data
            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8");

            // Send the login data
            connection.getOutputStream().write(data.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                String box = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    box += line + "\n";
                }
                inputStream.close();
                final String result = box;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText(result);
                        if (result.trim().equals("登入成功 3秒後切換進入主頁")) {
                            // Login successful, switch to the main page after 3 seconds
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
