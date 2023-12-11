package com.example.sportsworlddemo2.database;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.TextView;

import com.example.sportsworlddemo2.homepage.Home;
import com.example.sportsworlddemo2.homepage.Reservation1;
import com.example.sportsworlddemo2.reservationpage.Reservation4;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ReservationDB implements Runnable{
    private String sname,sId,equipment,eNo,date,time;
    private TextView textViewResult;
    private Reservation4 activity;
    private Handler handler;
    private Context context;


    public ReservationDB(String sname, String sId, String equipment, String eNo, String date, String time,
                         TextView textViewResult, Handler handler, Reservation4 activity, Context context) {
        this.sname = sname;
        this.sId = sId;
        this.equipment = equipment;
        this.eNo = eNo;
        this.date = date;
        this.time = time;
        this.textViewResult = textViewResult;
        this.handler = handler;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void run() {
        try {


            if (sname.isEmpty() || sId.isEmpty() || equipment.isEmpty() || eNo.isEmpty() || date.isEmpty() || time.isEmpty()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText("請填寫完整資訊");
                    }
                });
                // 使用者未輸入完整資訊，直接結束方法
                return;
            }
            //模擬器 10.0.2.2
            URL url = new URL("http://163.13.201.94/reservationdemo1.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.connect();

            String data = "sname=" + URLEncoder.encode(sname, "UTF-8")
                    + "&sId=" + URLEncoder.encode(sId, "UTF-8")
                    + "&equipment=" + URLEncoder.encode(equipment, "UTF-8")
                    + "&eNo=" + URLEncoder.encode(eNo, "UTF-8")
                    + "&date=" + URLEncoder.encode(date, "UTF-8")
                    + "&time=" + URLEncoder.encode(time, "UTF-8");

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
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        textViewResult.setText(result);
                        if (result.trim().equals("預約登記完成")) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // 建立一個 Intent 來切換到主頁面
                                    Intent intent = new Intent(context, Reservation1.class);
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
