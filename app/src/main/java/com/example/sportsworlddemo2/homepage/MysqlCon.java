package com.example.sportsworlddemo2.homepage;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlCon {

    // 資料庫定義
    String mysql_ip = "163.13.201.94";

    //String mysql_ip = "192.168.0.16"; //工作管理員效能ip
    int mysql_port = 3306; // Port 預設為 3306
    String db_name = "sports";
    String url = "jdbc:mysql://"+mysql_ip+":"+mysql_port+"/"+db_name+"?characterEncoding=latin1&useConfigs=maxPerformance";
    String db_user = "sports";
    String db_password = "00000000";

    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.v("DB","加載驅動成功");
        }catch( ClassNotFoundException e) {
            Log.e("DB","加載驅動失敗");
            return;
        }

        // 連接資料庫
        try {
            Connection con = DriverManager.getConnection(url,db_user,db_password);
            Log.v("DB","遠端連接成功");
        }catch(SQLException e) {
            Log.e("DB","遠端連接失敗");
            Log.e("DB", e.toString());
        }
    }

    public String getData() {
        String data = "";
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "SELECT value FROM sports.datalog";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next())
            {
                String result = rs.getString("value");
                data = result  ;
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public void insertData(String data) {
        try {
            Connection con = DriverManager.getConnection(url, db_user, db_password);
            String sql = "INSERT INTO  sports.datalog (result) VALUES ('" + data + "')";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            st.close();
            Log.v("DB", "寫入資料完成：" + data);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e("DB", "寫入資料失敗");
            Log.e("DB", e.toString());
        }
    }



}
