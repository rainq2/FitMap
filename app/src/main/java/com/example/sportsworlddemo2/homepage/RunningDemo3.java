package com.example.sportsworlddemo2.homepage;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportsworlddemo2.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RunningDemo3 extends AppCompatActivity implements OnMapReadyCallback {
    private List<LatLng> routePoints; // 储存路线的坐标点列表

    private int currentRouteIndex = 0; // 当前移动到的路线坐标点索引
    public int speed;//99999999999999
    private TextView speedTextView;
    TextView textView;
    private MapView mapView;
    private GoogleMap googleMap;

    // 当前路线的坐标点列表

    private FusedLocationProviderClient fusedLocationClient;


    //private Handler handler;//99999999999999
    private com.example.sportsworlddemo2.homepage.RunningDemo3.MoveRunnable moveRunnable;

    // 100 milliseconds

    private Marker personMarker;
    private Polyline routePolyline;
    private LatLng startLatLng;

    // MainActivity class
    private Location previousLocation;
    public double totalDistance = 0.0f; // 总里程数（公里）
    public double distance =0.0f;

    private TextView totalDistanceTextView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public int abc =1;



    public RunningDemo3() {
    }

    //onCreate(): 在Activity创建时调用，设置布局和初始化各种控件、变量等。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runningdemo3);

        ImageButton imageButton = findViewById(R.id.image500);
        imageButton.setOnClickListener(view -> {
            stopMoving();
            showRouteSelectionDialog();
            // 调用显示路线选择对话框的方法
        });

        // 检查位置权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果权限尚未授予，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        //showRouteButton = findViewById(R.id.showRouteButton);

        totalDistanceTextView = findViewById(R.id.totalDistanceTextView1);
        // 找到並初始化 UI 元件
        speedTextView = findViewById(R.id.speed_text_view1);

        mapView = findViewById(R.id.mapView500);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();//: 请求获取位置权限，用于定位功能。
        showRouteSelectionDialog();

    }

    private void showRouteSelectionDialog() {

        moveRunnable = new com.example.sportsworlddemo2.homepage.RunningDemo3.MoveRunnable();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_layout, null);
        builder.setView(dialogView);


        TextView dialogTitle = dialogView.findViewById(R.id.dialogTitle);
        ListView routeListView = dialogView.findViewById(R.id.routeListView);

        dialogTitle.setText("選擇路線");



        final String[] routes = {"淡水捷運站到漁人碼頭", "八卦山大佛休息區到清境農場", "----離開----"};
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, routes);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, routes);

        routeListView.setAdapter(adapter);

        final AlertDialog dialog = builder.create();
        routeListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRoute = routes[position];
            switch (selectedRoute) {
                case "淡水捷運站到漁人碼頭": {
                    //DistanceRead();
                    // 用户选择了淡水捷運站到漁人碼頭路线
                    LatLng startLatLng = new LatLng(25.168074, 121.445940); // 淡水捷運站坐标

                    LatLng endLatLng = new LatLng(25.183232486710043, 121.41214058320014); // 漁人碼頭坐标

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(startLatLng).title("淡水捷運站"));
                    googleMap.addMarker(new MarkerOptions().position(endLatLng).title("漁人碼頭"));
                    calculateRoute(startLatLng, endLatLng); // 计算并显示路线

                    //handler.postDelayed(moveRunnable, 5000);

                    LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
                    builder1.include(startLatLng);
                    builder1.include(endLatLng);
                    LatLngBounds bounds = builder1.build();

                    int padding = 50; // 調整地圖顯示範圍的邊距


                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));


                    break;
                }
                case "八卦山大佛休息區到清境農場": {
                    ///DistanceRead();
                    // 用户选择了八卦山到清境農場路線
                    LatLng startLatLng = new LatLng(24.099173887781923, 120.55019436785868); // 八卦山坐标

                    LatLng endLatLng = new LatLng(24.05931506588166, 121.16276170252397); // 清境農場坐标

                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(startLatLng).title("八卦山"));
                    googleMap.addMarker(new MarkerOptions().position(endLatLng).title("清境農場"));
                    calculateRoute(startLatLng, endLatLng); // 計算並顯示路線

                    //handler.postDelayed(moveRunnable, 5000);
                    LatLngBounds.Builder builder1 = new LatLngBounds.Builder();
                    builder1.include(startLatLng);
                    builder1.include(endLatLng);
                    LatLngBounds bounds = builder1.build();

                    int padding = 50; // 調整地圖顯示範圍的邊距


                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

                    break;
                }
                case "----離開----":
                    finish(); // 关闭当前 Activity，即关闭应用程序

                    break;
            }
            dialog.dismiss(); // 关闭对话框
        });
        dialog.show();

    }

    //createPersonMarker(): 创建人物标记并显示在地图上
    private void createPersonMarker(LatLng position) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.cat);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title("人物位置")
                .icon(icon);
        personMarker = googleMap.addMarker(markerOptions);
        handler.postDelayed(() -> {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(startLatLng);
            LatLngBounds bounds = builder.build();

            int padding = 50; // 调整地图显示范围的边距

            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 200f), 20, null);

        }, 2500);

    }
    //calculateRoute(): 根据起始位置和终点位置计算导航路线。
    private void calculateRoute(LatLng startLatLng, LatLng endLatLng) {

        this.startLatLng = startLatLng;
        // 清除舊的路線
        if (routePolyline != null) {
            routePolyline.remove();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getDirectionsUrl(startLatLng, endLatLng);


        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    LatLng startLocation;

                    try {
                        JSONArray routes = response.getJSONArray("routes");
                        if (routes.length() > 0) {
                            JSONObject route = routes.getJSONObject(0);
                            JSONObject legs = route.getJSONArray("legs").getJSONObject(0);
                            // 获取路线的起点和终点坐标
                            startLocation = new LatLng(
                                    legs.getJSONObject("start_location").getDouble("lat"),
                                    legs.getJSONObject("start_location").getDouble("lng"));
                            LatLng endLocation = new LatLng(
                                    legs.getJSONObject("end_location").getDouble("lat"),
                                    legs.getJSONObject("end_location").getDouble("lng"));

                            // 获取路线的坐标点列表
                            routePoints = decodePolyline(route.getJSONObject("overview_polyline").getString("points"));
                            // 获取路线的坐标点列表

                            drawRoute(startLocation, endLocation);
                            createPersonMarker(startLocation);
                            // 启动MoveRunnable以定时移动人物

                            previousLocation = null; // 將前一位置重置為 null
                            handler.postDelayed(moveRunnable, 5000);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(com.example.sportsworlddemo2.homepage.RunningDemo3.this, "无法取得路线信息", Toast.LENGTH_SHORT).show());

        queue.add(jsonRequest);
    }

//movePersonAlongRoute(): 在路线上移动人物。

    private void movePersonAlongRoute() {

        if (personMarker != null && routePoints != null && routePoints.size() > currentRouteIndex + 1) {
            LatLng startPosition = routePoints.get(currentRouteIndex);
            LatLng endPosition = routePoints.get(currentRouteIndex + 1);
            long duration = calculateDuration(startPosition, endPosition);

            final MarkerAnimation.LatLngInterpolator latLngInterpolator = new MarkerAnimation.LatLngInterpolator.LinearFixed();

            //如果您想要一个减速的动画效果，可以使用DecelerateInterpolator插值器：
            if (personMarker != null) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(duration);
                final Interpolator interpolator = new LinearInterpolator();
                valueAnimator.setInterpolator(interpolator);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float fraction = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(fraction, startPosition, endPosition);
                        if (personMarker != null) {
                            personMarker.setPosition(newPosition);
                        }


                        // 更新摄像机位置，使人物标记保持居中
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(newPosition)
                                .zoom(30) // 可根据需要设置适当的缩放级别
                                .build();
                        //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 10, null);
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                        // 更新里程数
                        if (previousLocation != null) {
                            if (abc != 0) {
                                distance = 0.0;
                                totalDistance = 0.0;
                            }
                            distance = calculateDistance(previousLocation, new LatLng(newPosition.latitude, newPosition.longitude));
                            totalDistance += distance;
                            totalDistanceTextView.setText(String.format("    %.2f", totalDistance));

                        }

// 在onCreate方法中的初始化部分添加以下代码
                        speedTextView = findViewById(R.id.speed_text_view1);
                        //speedTextView.setText("Speed: " + speed + " km/h");
                        speedTextView.setText("    " + speed + "");


                        previousLocation = new Location("");
                        previousLocation.setLatitude(newPosition.latitude);
                        previousLocation.setLongitude(newPosition.longitude);
                    }

                    //calculateDistance(): 根据经纬度计算距离。
                    private double calculateDistance(Location startLocation, LatLng endLatLng) {
                        double lat1 = startLocation.getLatitude();
                        double lon1 = startLocation.getLongitude();
                        double lat2 = endLatLng.latitude;
                        double lon2 = endLatLng.longitude;

                        // Rest of the method remains the same


                        double dLat = Math.toRadians(lat2 - lat1);
                        double dLon = Math.toRadians(lon2 - lon1);
                        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                        double earthRadius = 6371;
                        //return earthRadius * c * 1000;// 返回以公尺为单位的距离
                        return earthRadius * c; // 返回以公里为单位的距离
                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // 动画开始事件
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        currentRouteIndex++; // 移動到下一個位置
                        if (currentRouteIndex < routePoints.size() - 1) {
                            abc = 0;
                            movePersonAlongRoute(); // 繼續移動到下一個位置

                        } else {
                            // 顯示路線選擇對話框
                            abc++;
                            totalDistance = 0.0; // 重置總里程數
                            showRouteSelectionDialog();
                            currentRouteIndex = 0;
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // 动画取消事件
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // 动画重复事件
                    }
                });
                valueAnimator.start();
            }
        }
    }

    //drawRoute(): 在地图上绘制导航路线。
    // Modify the drawRoute method
    public void drawRoute(LatLng startLatLng, LatLng endLatLng) {
        this.startLatLng = startLatLng;
        String url = getDirectionsUrl(startLatLng, endLatLng);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    //LatLng startLocation = null;
                    try {
                        JSONArray routes = response.getJSONArray("routes");
                        JSONObject selectedRoute = routes.getJSONObject(0);
                        JSONObject polyline = selectedRoute.getJSONObject("overview_polyline");
                        String encodedPolyline = polyline.getString("points");

                        List<LatLng> points = decodePolyline(encodedPolyline);

                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(points);
                        polylineOptions.width(10);
                        polylineOptions.color(ContextCompat.getColor(com.example.sportsworlddemo2.homepage.RunningDemo3.this, R.color.colorPrimary));

                        routePolyline = googleMap.addPolyline(polylineOptions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(com.example.sportsworlddemo2.homepage.RunningDemo3.this, "無法獲取路線", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //decodePolyline(): 解码Polyline编码的路线坐标。
    private List<LatLng> decodePolyline(String encodedPolyline) {
        List<LatLng> points = new ArrayList<>();
        int index = 0;
        int latitude = 0, longitude = 0;

        while (index < encodedPolyline.length()) {
            int b;
            int shift = 0;
            int result = 0;

            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1F) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlatitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            latitude += dlatitude;

            shift = 0;
            result = 0;

            do {
                b = encodedPolyline.charAt(index++) - 63;
                result |= (b & 0x1F) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlongitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            longitude += dlongitude;

            double lat = latitude / 1E5;
            double lng = longitude / 1E5;

            LatLng point = new LatLng(lat, lng);
            points.add(point);
        }

        return points;
    }
    //-----------------------------------------------------------------------------------------
    //requestLocationPermission(): 请求获取位置权限，用于定位功能。
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            getLastLocation();
        }
    }
    //getLastLocation(): 获取最后一次位置信息并在地图上显示。
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                LatLng initialPosition = new LatLng(latitude, longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 50f));
            }
        });
    }

    // 修改 stopMoving 方法，停止人物移动并移除移动任务
    private void stopMoving() {

        abc++;
        handler.removeCallbacks(moveRunnable); // 移除之前的移动任务
        // 如果有正在移动的人物标记，将其移除
        if (personMarker != null) {
            personMarker.remove();
            personMarker = null;
        }
        // 重置当前路线索引
        currentRouteIndex = 0;
    }

    //onMapReady(): 当地图准备就绪时调用，初始化Google地图并设置初始位置。
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        checkLocationPermission();
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // 普通地图
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); // 混合地图
    }

    //checkLocationPermission(): 检查位置权限并启用地图上的"我的位置"功能。
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
    //onRequestPermissionsResult(): 处理权限请求结果。
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }
    //enableMyLocation(): 启用地图上的"我的位置"功能。
    private void enableMyLocation() {
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
        }
    }
    //calculateDuration(): 根据起点和终点计算人物移动的持续时间。
    private long calculateDuration(LatLng startLocation, LatLng endLocation) {
        double distance = calculateDistance(startLocation, endLocation);
        double durationInMilliseconds = (distance / speed) * 1000;
        return (long) durationInMilliseconds;
    }
    //calculateDistance(): 根据起点和终点计算距离。
    private double calculateDistance(LatLng startLocation, LatLng endLocation) {
        double lat1 = startLocation.latitude;
        double lon1 = startLocation.longitude;
        double lat2 = endLocation.latitude;
        double lon2 = endLocation.longitude;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double earthRadius = 6371;
        return earthRadius * c * 1000;
    }
    //getDirectionsUrl(): 根据起点和终点坐标获取Google地图API的请求URL。
    private String getDirectionsUrl(LatLng startLatLng, LatLng endLatLng) {
        String apiKey = "AIzaSyAc8fhNfH6cUNllf8lV85neCLb6vyG5B2Y";
        String origin = startLatLng.latitude + "," + startLatLng.longitude;
        String destination = endLatLng.latitude + "," + endLatLng.longitude;
        return "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=" + origin +
                "&destination=" + destination +
                /*"&mode=walking" +  // Add the walking mode parameter*/
                "&mode=bicycling" +  // Change to bicycling mode
                "&key=" + apiKey;
    }

    //onResume(): Activity恢复时调用，恢复地图的生命周期。
    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
            fetchDataAndUpdate(); // 在恢復時重新開始數據更新
        }
    }
    //onPause(): Activity暂停时调用，暂停地图的生命周期。
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    //onDestroy(): Activity销毁时调用，销毁地图和停止人物移动。
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        stopMoving(); // Stop the MoveRunnable
    }
    //MoveRunnable: 一个Runnable实现，用于在指定的延迟时间内定时移动人物。
    private class MoveRunnable implements Runnable {
        @Override
        public void run() {
            movePersonAlongRoute();

        }
    }
    private void fetchDataAndUpdate() {
        // 每隔三秒更新一次資料
        Runnable fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                new com.example.sportsworlddemo2.homepage.RunningDemo3.FetchDataTask().execute();
                handler.postDelayed(this, 3000); // 每隔三秒更新一次資料
            }
        };
        handler.post(fetchDataRunnable);
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                //URL url = new URL("http://192.168.0.69/get_speed_data.php");
                URL url = new URL("http://163.13.201.94/get_speed_data.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), 8);
                    StringBuilder box = new StringBuilder();
                    String line;
                    while ((line = bufReader.readLine()) != null) {
                        box.append(line).append("\n");
                    }
                    inputStream.close();
                    return box.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                result = result.trim(); // 去除字符串两端的空白字符和换行符
                try {
                    int newSpeed = Integer.parseInt(result);
                    Log.d("SpeedDebug", "Parsed speed: " + newSpeed);

                    if (newSpeed > 0) {
                        speed = newSpeed; // 将抓取到的數值設置為新的 speed
                        speedTextView.setText("    " + speed + ""); // 更新顯示在 UI 上的 speed

                        // 如果需要将 speed 传递到其他地方，可以在这里进行处理
                        // 其他逻辑...
                    }
                } catch (NumberFormatException e) {
                    Log.e("SpeedDebug", "Error parsing speed: " + e.getMessage());
                }
                //showNotification("更新資料", "資料已更新");
            } else {
                textView.setText("取得資料失敗");
            }
        }
    }
}
