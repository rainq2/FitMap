package com.example.sportsworlddemo2.homepage.trainingpage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsworlddemo2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class edit_chest_menu extends AppCompatActivity {
    private ListView menuListView;
    private static final int REQUEST_CODE_ADD_MENU_ITEM = 1;

    private Button btn_add_action;
    private ArrayAdapter<String> menuAdapter;
    private ArrayList<String> menuDataList = new ArrayList<>();
    private HashSet<String> selectedItems = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_chest_menu);

        Button btnDelete = findViewById(R.id.btn_delete);
        Spinner deleteSpinner = findViewById(R.id.deletespinner);
        menuListView = findViewById(R.id.menuListView);
        // 修改适配器的初始化部分
        menuAdapter = new ArrayAdapter<String>(this, R.layout.list_item_layout, R.id.textView146, menuDataList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                CheckBox checkBox = view.findViewById(R.id.checkBox);

                // 根据选中状态设置复选框的可见性
                if (selectedItems.contains(getItem(position))) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }

                // 设置复选框的点击监听器
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkBox.isChecked()) {
                            selectedItems.add(getItem(position));
                        } else {
                            selectedItems.remove(getItem(position));
                        }
                    }
                });

                return view;
            }
        };
        menuListView.setAdapter(menuAdapter);

        new FetchDataTask().execute();

        // 设置删除按钮的点击监听器
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = deleteSpinner.getSelectedItemPosition();
                if (selectedPosition != AdapterView.INVALID_POSITION) {
                    String selectedItem = menuDataList.get(selectedPosition);
                    new DeleteDataTask().execute(selectedItem);
                }

            }
        });

        // 设置下拉选项的适配器
        ArrayAdapter<String> deleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuDataList);
        deleteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteSpinner.setAdapter(deleteAdapter);

        // 添加选择监听器
        deleteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = menuDataList.get(position);
                new DeleteDataTask().execute(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btn_add_action = findViewById(R.id.btn_add_action);
        btn_add_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_chest_menu.this, Training_chestedit.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_MENU_ITEM);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String selectedItem : selectedItems) {
                    new DeleteDataTask().execute(selectedItem);
                }
                selectedItems.clear();
            }
        });

    }

    class FetchDataTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> dataList = new ArrayList<>();
            try {
                // 發送 GET 請求以獲取胸部訓練菜單的資料
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

                    // 解析 JSON 響應
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String action = jsonObject.getString("action");
                        dataList.add(action);
                    }
                } else {
                    Log.e("HTTPError", "Error: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (!result.isEmpty()) {
                menuDataList.clear();
                menuDataList.addAll(result);
                menuAdapter.notifyDataSetChanged();
            } else {
                Log.d("chestMenu", "No data retrieved from server.");
            }
        }
    }


    class DeleteDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String selectedItem = strings[0];
            try {
                // 發送 POST 請求刪除選定的項目
                URL url = new URL("http://163.13.201.94/delete_chest_menu_item.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // 傳遞要刪除的項目名稱
                String postData = "action=" + selectedItem;
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(postData);
                writer.flush();
                writer.close();
                outputStream.close();

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
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // 在数据删除成功后，更新页面数据
            new FetchDataTask().execute();

            // 在2秒后跳转到前一页
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish(); // 结束当前页面
                }
            }, 2000); // 延时2秒
        }
    }
}



