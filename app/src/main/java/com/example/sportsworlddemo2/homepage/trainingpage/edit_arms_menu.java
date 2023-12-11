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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
public class edit_arms_menu extends AppCompatActivity{
    private ListView menuListView_arms;
    private static final int REQUEST_CODE_ADD_MENU_ITEM = 1;

    private Button btn_add_action_arms;
    private ArrayAdapter<String> menuAdapter;
    private ArrayList<String> menuDataList = new ArrayList<>();
    private HashSet<String> selectedItems = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_arms_menu);

        Button btnDelete_arms = findViewById(R.id.btn_delete_arms);
        Spinner deleteSpinner_arms = findViewById(R.id.deletespinner_arms);
        menuListView_arms = findViewById(R.id.menuListView_arms);
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
        menuListView_arms.setAdapter(menuAdapter);

        new FetchDataTask().execute();


        btnDelete_arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = deleteSpinner_arms.getSelectedItemPosition();
                if (selectedPosition != AdapterView.INVALID_POSITION) {
                    String selectedItem = menuDataList.get(selectedPosition);
                    new DeleteDataTask().execute(selectedItem);
                }

            }
        });

        // 设置下拉选项的适配器
        ArrayAdapter<String> deleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menuDataList);
        deleteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deleteSpinner_arms.setAdapter(deleteAdapter);

        // 添加选择监听器
        deleteSpinner_arms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        btn_add_action_arms = findViewById(R.id.btn_add_action_arms);
        btn_add_action_arms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_arms_menu.this, Training_armsedit.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_MENU_ITEM);
            }
        });
        btnDelete_arms.setOnClickListener(new View.OnClickListener() {
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
                Class.forName("com.mysql.jdbc.Driver");
                String databaseUrl = "jdbc:mysql://163.13.201.94:3306/sports?characterEncoding=UTF-8";
                Connection connection = DriverManager.getConnection(databaseUrl, "sports", "00000000");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM armsMenu");

                while (resultSet.next()) {
                    String action = resultSet.getString("action");
                    dataList.add(action);
                }

                connection.close();
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
                Log.d("armsMenu", "No data retrieved from database.");
            }
           /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(edit_arms_menu.this, Training_arms.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }, 0);*/
        }
    }

    class DeleteDataTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String selectedItem = strings[0];
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String databaseUrl = "jdbc:mysql://163.13.201.94:3306/sports?characterEncoding=UTF-8";
                Connection connection = DriverManager.getConnection(databaseUrl, "sports", "00000000");
                Statement statement = connection.createStatement();
                String deleteQuery = "DELETE FROM armsMenu WHERE action='" + selectedItem + "'";
                statement.executeUpdate(deleteQuery);
                connection.close();
            } catch (Exception e) {
                Log.e("Database", "Error: " + e.toString());
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
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
