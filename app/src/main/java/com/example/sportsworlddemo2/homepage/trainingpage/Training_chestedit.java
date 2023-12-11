package com.example.sportsworlddemo2.homepage.trainingpage;

import android.annotation.SuppressLint;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Training_chestedit extends AppCompatActivity {
    EditText editSport;
    Button addButton, Buttondelete, Buttonconfirm1;
    LinearLayout rootView;
    ArrayList<String> sss = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    TextView textViewResult_chest_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_chestedit);

        editSport = findViewById(R.id.textView66);
        addButton = findViewById(R.id.button13);
        Buttondelete = findViewById(R.id.button14);
        Buttonconfirm1 = findViewById(R.id.button15);
        textViewResult_chest_edit = findViewById(R.id.textView60);
        rootView = findViewById(R.id.linearLayout11);

        Buttonconfirm1.setOnClickListener(view -> saveDataToDatabase());

        addButton.setOnClickListener(view -> {
            String newsport = editSport.getText().toString().trim();
            if (newsport.isEmpty()) {
                textViewResult_chest_edit.setText("請輸入菜單");
                return;
            }

            sss.add(newsport);
            spinnerAdapter.notifyDataSetChanged();
            addTextViewToLayout();

            editSport.setText("");
        });

        Buttondelete.setOnClickListener(view -> {
            if (sss.isEmpty()) {
                Toast.makeText(Training_chestedit.this, "沒有可刪除的數值", Toast.LENGTH_SHORT).show();
                return;
            }

            sss.remove(sss.size() - 1);
            spinnerAdapter.notifyDataSetChanged();
            removeLastViewFromLayout();
        });

        Spinner spinner = findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sss) {
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

    private void addTextViewToLayout() {
        String numberedValue = (sss.size()) + ". " + sss.get(sss.size() - 1);
        TextView textView = new TextView(this);
        textView.setText(numberedValue);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        rootView.addView(textView);
    }

    private void removeLastViewFromLayout() {
        rootView.removeViewAt(rootView.getChildCount() - 1);
    }

    private void saveDataToDatabase() {
        JSONArray jsonArray = new JSONArray(sss);
        sendChestDataToServer(jsonArray);
    }

    @SuppressLint("StaticFieldLeak")
    private void sendChestDataToServer(JSONArray chestData) {
        new SaveDataToDatabaseTask().execute(chestData.toString());
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveDataToDatabaseTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String result;
            try {
                String serverUrl = "http://163.13.201.94/chest_menu_edit_demo1.php";
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                // 將資料包裝成 JSON 格式並送到伺服器
                JSONObject postData = new JSONObject();
                postData.put("chest_data", new JSONArray(strings[0]));
                String data = "data=" + URLEncoder.encode(postData.toString(), "UTF-8");
                connection.getOutputStream().write(data.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    StringBuilder box = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        box.append(line).append("\n");
                    }
                    inputStream.close();
                    result = box.toString().trim();
                } else {
                    result = "HTTP response code: " + responseCode;
                }
            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult_chest_edit.setText(result);
            if (result.equals("新增成功！")) {
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(Training_chestedit.this, Training_chest.class);
                    startActivity(intent);
                }, 3000);
            }
        }
    }
}
