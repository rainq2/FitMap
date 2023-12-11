package com.example.sportsworlddemo2.homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sportsworlddemo2.R;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class RunningDemo2 extends AppCompatActivity  implements OnMapReadyCallback{
    ImageButton btn1,btn2,btn3,btn4,btn5,setting;
    @SuppressLint("MissingInflatedId")

    private List<LatLng> routePoints; // 储存路线的坐标点列表

    private int currentRouteIndex = 0; // 当前移动到的路线坐标点索引
    public double speed=10f;
    private MapView mapView;
    private GoogleMap googleMap;

    private List<LatLng> currentRoutePoints; // 当前路线的坐标点列表

    private FusedLocationProviderClient fusedLocationClient;


    private Handler handler;
    private MoveRunnable moveRunnable;

    private static final long MOVE_DELAY = 20; // 100 milliseconds

    private Marker personMarker;
    private Polyline routePolyline;
    private LatLng startLatLng;
    private LatLng endLatLng;
    //onCreate(): 在Activity创建时调用，设置布局和初始化各种控件、变量等。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runningdemo2);

        setting = (ImageButton) findViewById(R.id.imageButton30);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(RunningDemo2.this,Setting.class);
                startActivity(intent);
            }
        });

        //取得按鍵

        btn1 = findViewById(R.id.imageButton24);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RunningDemo2.this, Reservation1.class);
                startActivity(intent);
            }
        });

        btn3 = findViewById(R.id.imageButton26);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RunningDemo2.this, Home.class);
                startActivity(intent);
            }
        });

        btn4 = findViewById(R.id.imageButton28);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RunningDemo2.this, Training.class);
                startActivity(intent);
            }
        });

        btn5 = findViewById(R.id.imageButton29);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RunningDemo2.this, Article_food.class);
                startActivity(intent);
            }
        });


        //showRouteButton = findViewById(R.id.showRouteButton);

        mapView = findViewById(R.id.mapView500);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermission();

        handler = new Handler();
        moveRunnable = new MoveRunnable();



        // 添加对话框逻辑
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("選擇路線");
        final String[] routes = {"淡水捷運站到漁人碼頭", "其他路线1", "其他路线2"}; // 添加其他路线选项
        builder.setItems(routes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedRoute = routes[which];
                if (selectedRoute.equals("淡水捷運站到漁人碼頭")) {
                    // 用户选择了淡水捷運站到漁人碼頭路线
                    LatLng startLatLng = new LatLng(25.168074, 121.445940); // 淡水捷運站坐标
                    LatLng endLatLng = new LatLng(25.169640, 121.444306); // 漁人碼頭坐标
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(startLatLng).title("淡水捷運站"));
                    googleMap.addMarker(new MarkerOptions().position(endLatLng).title("漁人碼頭"));
                    calculateRoute(startLatLng, endLatLng); // 计算并显示路线

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(startLatLng);
                    builder.include(endLatLng);
                    LatLngBounds bounds = builder.build();

                    int padding = 200; // 調整地圖顯示範圍的邊距

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));



                } else {
                    // 处理其他路线选项
                }
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();

        // 调用movePersonAlongRoute()方法来开始移动人物标记
        //handler.postDelayed(moveRunnable, 5000); // 5 秒後開始移動
    }





    //createPersonMarker(): 创建人物标记并显示在地图上
    private void createPersonMarker(LatLng position) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.cat);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title("人物位置")
                .icon(icon);
        personMarker = googleMap.addMarker(markerOptions);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(startLatLng);
                LatLngBounds bounds = builder.build();

                int padding = 200; // 调整地图显示范围的边距

                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 200f), 20, null);

            }
        }, 2500);

    }

    //calculateRoute(): 根据起始位置和终点位置计算导航路线。
    private void calculateRoute(LatLng startLatLng, LatLng endLatLng) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getDirectionsUrl(startLatLng, endLatLng);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LatLng startLocation = null;

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
                                currentRoutePoints = decodePolyline(route.getJSONObject("overview_polyline").getString("points"));


                                drawRoute(startLocation, endLocation);
                                createPersonMarker(startLocation);

                                //movePersonAlongRoute();
                                // 启动MoveRunnable以定时移动人物
                                handler.postDelayed(moveRunnable, 5000);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RunningDemo2.this, "无法取得路线信息", Toast.LENGTH_SHORT).show();
            }
        });

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
            //final Interpolator interpolator = new DecelerateInterpolator();
            //valueAnimator.setInterpolator(interpolator);


            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(duration);
            final Interpolator interpolator = new LinearInterpolator();
            valueAnimator.setInterpolator(interpolator);
        /*final Interpolator interpolator = new AccelerateDecelerateInterpolator();
        valueAnimator.setInterpolator(interpolator);*/

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float fraction = animation.getAnimatedFraction();
                    LatLng newPosition = latLngInterpolator.interpolate(fraction, startPosition, endPosition);
                    personMarker.setPosition(newPosition);



                    // 更新摄像机位置，使人物标记保持居中
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(newPosition)
                            .zoom(130f) // 可根据需要设置适当的缩放级别
                            .build();
                    //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 10, null);
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    // 动画开始事件
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // 动画结束事件
                    currentRouteIndex++; // 移动到下一个位置
                    if (currentRouteIndex < routePoints.size() - 1) {
                        movePersonAlongRoute(); // 继续移动到下一个位置
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



    //drawRoute(): 在地图上绘制导航路线。
    // Modify the drawRoute method
    public void drawRoute(LatLng startLatLng, LatLng endLatLng) {
        this.startLatLng = startLatLng;
        this.endLatLng = endLatLng;
        String url = getDirectionsUrl(startLatLng, endLatLng);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            polylineOptions.color(ContextCompat.getColor(RunningDemo2.this, R.color.colorPrimary));

                            routePolyline = googleMap.addPolyline(polylineOptions);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RunningDemo2.this, "無法獲取路線", Toast.LENGTH_SHORT).show();
                    }
                });

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
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    LatLng initialPosition = new LatLng(latitude, longitude);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 15f));
                }
            }
        });
    }
    //stopMoving(): 停止移动人物
    private void stopMoving() {
        handler.removeCallbacks(moveRunnable);
    }
    //onMapReady(): 当地图准备就绪时调用，初始化Google地图并设置初始位置。
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        checkLocationPermission();
        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // 普通地图
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID); // 混合地图


    }
    //getLocationFromAddress(): 根据地址获取经纬度坐标。
    private LatLng getLocationFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;
        LatLng location = null;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address addressResult = addressList.get(0);
                double latitude = addressResult.getLatitude();
                double longitude = addressResult.getLongitude();
                location = new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
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
        String apiKey = "AIzaSyBKLsTb-u5D9lOg-cVVu264lNo5YMxuNvo";
        String origin = startLatLng.latitude + "," + startLatLng.longitude;
        String destination = endLatLng.latitude + "," + endLatLng.longitude;
        String url = "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=" + origin +
                "&destination=" + destination +
                "&mode=walking" +  // Add the walking mode parameter
                "&key=" + apiKey;
        return url;
    }

    //onResume(): Activity恢复时调用，恢复地图的生命周期。
    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
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
        stopMoving();
    }
    //MoveRunnable: 一个Runnable实现，用于在指定的延迟时间内定时移动人物。
    // MoveRunnable: 一个Runnable实现，用于在指定的延迟时间后定时移动人物。
    private class MoveRunnable implements Runnable {

        @Override
        public void run() {

            movePersonAlongRoute();
        }
    }

}
