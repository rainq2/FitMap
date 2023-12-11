package com.example.sportsworlddemo2.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportsworlddemo2.R;
import com.example.sportsworlddemo2.reservationpage.Reservation2;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Reservation1 extends AppCompatActivity {
    TextView textView;
    String result;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable fetchDataRunnable;

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "my_channel";

    ImageButton Ibtn1,Ibtn2,Ibtn3,Ibtn4,Ibtn5,setting;
    Button btn1;
    Calendar calendar = Calendar.getInstance();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation1);

        createNotificationChannel(); // 創建通知頻道

        fetchDataAndUpdate();

        Button deleteButton1 = findViewById(R.id.button47);
        deleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v.getParent(); // 获取按钮所在的LinearLayout
                TextView textView = (TextView) layout.getChildAt(0); // 获取LinearLayout中的第一个TextView
                String dataId = textView.getText().toString(); // 获取TextView的值

                deleteData(dataId); // 调用删除数据的方法
            }
        });
        Button deleteButton2 = findViewById(R.id.button48);
        deleteButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v.getParent(); // 获取按钮所在的LinearLayout
                TextView textView = (TextView) layout.getChildAt(0); // 获取LinearLayout中的第一个TextView
                String dataId = textView.getText().toString(); // 获取TextView的值

                deleteData(dataId); // 调用删除数据的方法
            }
        });
        Button deleteButton3 = findViewById(R.id.button49);
        deleteButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v.getParent(); // 获取按钮所在的LinearLayout
                TextView textView = (TextView) layout.getChildAt(0); // 获取LinearLayout中的第一个TextView
                String dataId = textView.getText().toString(); // 获取TextView的值

                deleteData(dataId); // 调用删除数据的方法
            }
        });
        Button deleteButton4 = findViewById(R.id.button50);
        deleteButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout layout = (LinearLayout) v.getParent(); // 获取按钮所在的LinearLayout
                TextView textView = (TextView) layout.getChildAt(0); // 获取LinearLayout中的第一个TextView
                String dataId = textView.getText().toString(); // 获取TextView的值

                deleteData(dataId); // 调用删除数据的方法
            }
        });





        Button sosButton = findViewById(R.id.button57);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 檢查權限是否已授予
                if (ContextCompat.checkSelfPermission(Reservation1.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 如果未授予權限，則請求權限
                    ActivityCompat.requestPermissions(Reservation1.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    // 如果已授予權限，
                    //ACTION_CALL是馬上打
                    //ACTION_DIAL開啟撥號介面
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:119"));
                    startActivity(callIntent);
                }
            }
        });


        btn1 = findViewById(R.id.button5);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Reservation1.this, Reservation2.class);
                startActivity(intent);
            }
        });


        Ibtn2 = findViewById(R.id.imageButton3);
        Ibtn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.setClass(Reservation1.this, Running.class);
                intent.setClass(Reservation1.this, RunningDemo3.class);
                startActivity(intent);
            }
        });

        Ibtn3 = findViewById(R.id.imageButton4);
        Ibtn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Reservation1.this, Home.class);
                startActivity(intent);
            }
        });


        Ibtn4 = findViewById(R.id.imageButton5);
        Ibtn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Reservation1.this, Training.class);
                startActivity(intent);
            }
        });

        Ibtn5 = findViewById(R.id.imageButton6);
        Ibtn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Reservation1.this, Article_food.class);
                startActivity(intent);
            }
        });

        setting = (ImageButton) findViewById(R.id.image500);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Reservation1.this,Setting.class);
                startActivity(intent);
            }
        });
    }



    public void fetchDataAndUpdate() {
        fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                new FetchDataTask().execute();
                handler.postDelayed(this, 60000); // 每隔60秒更新一次資料
            }
        };
        handler.post(fetchDataRunnable);
    }

    private class FetchDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://163.13.201.94/reservationdemo_getdata.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    String box = "";
                    String line = null;
                    while ((line = bufReader.readLine()) != null) {
                        box += line + "\n";
                    }
                    inputStream.close();
                    return box;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            Log.d("FetchDataTask", "Received data from server: " + result);

            if (result != null) {
                Log.d("FetchDataTask", "Data fetched successfully");

                // 先移除日期中的 <br> 標記
                result = result.replaceAll("<br>", "");

                // 將取得的資料以換行符號分隔成字串陣列
                String[] values = result.split("\n");
                Log.d("FetchDataTask", "Number of values: " + values.length); // 新增這行程式碼，檢查陣列長度
                for (String value : values) {
                    Log.d("FetchDataTask", "Value: " + value);
                }
                for (int i = 0; i < values.length; i++) {
                    // 移除 HTML 標籤
                    values[i] = android.text.Html.fromHtml(values[i]).toString();
                    // 確保日期格式是 "yyyy/MM/dd"
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/M/d", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

                    try {
                        Date date = inputFormat.parse(values[i]);
                        values[i] = outputFormat.format(date);
                    } catch (ParseException e) {
                        // 如果日期解析失敗，不進行任何修改
                    }
                }
                // 假设您的TextView的ID数组如下
                int[] textViewIds = {
                        R.id.UserIDText1, R.id.UserIDText2, R.id.UserIDText3,
                        R.id.UserIDText4, R.id.UserIDText5, R.id.UserIDText6,
                        R.id.UserIDText7, R.id.UserIDText8, R.id.UserIDText9,
                        R.id.UserIDText10, R.id.UserIDText11, R.id.UserIDText12
                };

                int textViewCount = textViewIds.length;
                if (values.length >= textViewCount) {
                    for ( int i = 0; i < textViewCount && i < values.length; i++) {
                        TextView textView = findViewById(textViewIds[i]);
                        textView.setText(values[i]);
                    }
                } else {
                    Log.d("FetchDataTask", "Not enough values in the result.");
                }
            } else {
                Log.d("FetchDataTask", "Failed to fetch data.");
                // editText1.setText("取得資料失敗"); 這行可能需要根據你的程式碼進行修改
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    //刪除button
    private void deleteData(String dataId) {
        new DeleteDataTask().execute(dataId);
    }
    private class DeleteDataTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String dataId = params[0]; // 获取要删除的数据的标识
                Log.d("data_id==", dataId);
                URL url = new URL("http://163.13.201.94/delete_data.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true); // 设置允许输出数据
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置请求类型
                Log.d("FetchDataTask", "444");
                String postData = "data_id=" + URLEncoder.encode(dataId, "UTF-8");
                Log.d("FetchDataTask", postData);
                // 获取输出流，将请求参数写入
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(postData.getBytes("UTF-8"));
                outputStream.close();

                // 获取响应码
                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean success) {
            if (success) {
                fetchDataAndUpdate(); // 更新UI
            } else {
                // 删除失败的处理
                Snackbar.make(findViewById(R.id.linearLayout4), "删除失败", Snackbar.LENGTH_SHORT).show();
            }
        }
    }





    // 接收權限請求的結果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果用戶授予權限，則執行打電話的程式碼
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:119"));
                startActivity(callIntent);
            }
        }
    }
}
