package com.example.sportsworlddemo2.homepage.settingpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Personal extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_personal);

        EditText editUsername, editPassword, editPhone, editEmail,editHeight,editWeight;
        Button UpdateButton;
        TextView Result;

        editUsername = findViewById(R.id.textView89);
        editPassword = findViewById(R.id.textView91);
        editEmail = findViewById(R.id.textView93);
        editPhone = findViewById(R.id.textView95);
        editHeight = findViewById(R.id.textView143);
        editWeight = findViewById(R.id.textView145);


        UpdateButton = findViewById(R.id.button38);
        Result = findViewById(R.id.textView96);
        //editUsername.setEnabled(false); // 设置为不可编辑

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String height = editHeight.getText().toString().trim();
                String weight = editWeight.getText().toString().trim();



                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || height.isEmpty() || weight.isEmpty())  {
                    Result.setText("請填寫完整資訊");
                    return;
                }

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //模擬器 10.0.2.2
                            URL url = new URL("http://163.13.201.94/personaldemo1.php");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setDoInput(true);
                            connection.setUseCaches(false);
                            connection.connect();

                            String data = "username=" + URLEncoder.encode(username, "UTF-8")
                                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                                    + "&email=" + URLEncoder.encode(email, "UTF-8")
                                    + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                                    + "&height=" + URLEncoder.encode(height, "UTF-8")
                                    + "&weight=" + URLEncoder.encode(weight, "UTF-8");


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
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Result.setText(result);
                                    }
                                });
                            } else {
                                throw new Exception("HTTP response code: " + responseCode);
                            }
                        } catch (Exception e) {
                            final String error = e.toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Result.setText(error);
                                }
                            });
                        }
                    }
                });
                thread.start();
            }
        });
    }
}
