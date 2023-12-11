package com.example.sportsworlddemo2.database;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sportsworlddemo2.Login;
import com.example.sportsworlddemo2.Register;
import com.example.sportsworlddemo2.homepage.Home;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterDB implements Runnable {
    private EditText editUsername;
    private EditText editPassword;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editHeight;
    private EditText editWeight;
    private TextView textViewResult;
    private Handler handler;
    private Register activity;
    private Context context;

    public RegisterDB(EditText editUsername, EditText editPassword, EditText editPhone, EditText editEmail,
                      EditText editHeight, EditText editWeight, TextView textViewResult, Handler handler, Register activity, Context context) {
        this.editUsername = editUsername;
        this.editPassword = editPassword;
        this.editPhone = editPhone;
        this.editEmail = editEmail;
        this.editHeight = editHeight;
        this.editWeight = editWeight;
        this.textViewResult = textViewResult;
        this.handler = handler;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String height = editHeight.getText().toString().trim();
            String weight = editWeight.getText().toString().trim();


            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || height.isEmpty() || weight.isEmpty()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText("請填寫完整資訊");
                    }
                });
                // 使用者未輸入完整資訊，直接結束方法
                return;
            }


            // Connect to the registration PHP script
            URL url = new URL("http://163.13.201.94/registerdemo1.php");
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

            // Prepare the registration data
            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&email=" + URLEncoder.encode(email, "UTF-8")
                    + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                    + "&height=" + URLEncoder.encode(height, "UTF-8")
                    + "&weight=" + URLEncoder.encode(weight, "UTF-8");

            // Send the registration data
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
                        if (result.trim().equals("註冊成功！")) {
                            // Registration successful, switch to the login page after 3 seconds
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 建立一個 Intent 來切換到主頁面
                                    Intent intent = new Intent(context, Login.class);
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