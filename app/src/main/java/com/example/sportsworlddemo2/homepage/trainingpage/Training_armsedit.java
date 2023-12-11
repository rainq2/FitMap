package com.example.sportsworlddemo2.homepage.trainingpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Training_armsedit extends AppCompatActivity {
    Button addButton, Buttondelete, Buttonconfirm1;
    LinearLayout arms_edit_root_View;
    ArrayList<String> sss = new ArrayList<String>();
    ArrayAdapter<String> spinnerAdapter;
    EditText editSport;
    TextView textViewResult_arms_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainging_armsedit);

        arms_edit_root_View = findViewById(R.id.arms_edit_root_View);
        editSport = findViewById(R.id.textView108);
        addButton = findViewById(R.id.button87);
        Buttondelete = findViewById(R.id.button88);
        Buttonconfirm1 = findViewById(R.id.button89);
        textViewResult_arms_edit = findViewById(R.id.textView109);

        Buttonconfirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToDatabase();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newsport = editSport.getText().toString().trim();
                if (newsport.isEmpty()) {
                    textViewResult_arms_edit.setText("請輸入菜單");
                    return;
                }

                // 添加到列表
                sss.add(newsport);
                spinnerAdapter.notifyDataSetChanged();

                // 动态添加到LinearLayout中
                String numberedValue = (sss.size()) + ". " + newsport;
                TextView textView = new TextView(Training_armsedit.this);
                textView.setText(numberedValue);
                textView.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                arms_edit_root_View.addView(textView);

                // 清空EditText
                editSport.setText("");
            }
        });

        Buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sss.isEmpty()) {
                    Toast.makeText(Training_armsedit.this, "沒有可刪除的數值", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 删除最后一个元素
                sss.remove(sss.size() - 1);
                spinnerAdapter.notifyDataSetChanged();

                // 从LinearLayout中删除最后一个View
                arms_edit_root_View.removeViewAt(arms_edit_root_View.getChildCount() - 1);
            }
        });

        Spinner spinner = findViewById(R.id.spinner4);
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sss) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                String item = getItem(position);
                String numberedItem = (position + 1) + ". " + item;
                textView.setText(numberedItem);
                return textView;
            }
        };
        spinner.setAdapter(spinnerAdapter);
    }

    private void saveDataToDatabase() {
        String[] sportsArray = sss.toArray(new String[0]);

        // 将数据转换为JSON数组
        JSONArray jsonArray = new JSONArray();
        for (String sport : sportsArray) {
            jsonArray.put(sport);
        }

        // 调用Web服务保存数据
        new SaveDataToDatabaseTask().execute(jsonArray.toString());
    }

    private class SaveDataToDatabaseTask extends AsyncTask<String, Void, String> {
        //String newsport = editSport.getText().toString().trim();
        @Override
        protected String doInBackground(String... strings) {
            String result;
            try {
                String serverUrl = "http://163.13.201.94/arms_menu.php";
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                JSONObject postData = new JSONObject();
                postData.put("arms_data", new JSONArray(strings[0]));
                String data = "data=" + URLEncoder.encode(postData.toString(), "UTF-8");
                connection.getOutputStream().write(data.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    StringBuilder box = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        box.append(line).append("\n");
                    }
                    inputStream.close();
                    result = box.toString().trim();
                } else {
                    result = "HTTP response code: " + responseCode;
                }
                connection.disconnect(); // 斷開連接
            } catch (Exception e) {
                result = "Error: " + e.toString();
            }
            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            textViewResult_arms_edit.setText(result);
            if (result.equals("新增成功！")) {
                // 添加成功后，跳转到Training_arms页面
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Training_armsedit.this, Training_arms.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, 3000);
            }
        }
    }
}
