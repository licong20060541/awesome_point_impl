package com.point.impl;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by licong12 on 2018/8/29.
 */

public class GPSInfoActivity extends Activity {

    TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        textView = findViewById(R.id.tv_simple);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
        doWork();
    }

    private void doWork() {

        //获取一个地址管理者，获取的方法比较特殊，不是直接new出来的
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //获取时时更新，第一个是Provider,第二个参数是更新时间1000ms，第三个参数是更新半径，第四个是监听器
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("licong", "gps");
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 8, new LocationListener() {

            @Override
            /*当地理位置发生改变的时候调用*/
            public void onLocationChanged(final Location location) {

                Log.d("licong", "" + location.toString());
                Log.d("licong", "time: " + location.getTime());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("loc time: " + location.getTime());
                    }
                });

            }

            /* 当状态发生改变的时候调用*/
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("GPS_SERVICES", "状态信息发生改变");

            }

            /*当定位者启用的时候调用*/
            @Override
            public void onProviderEnabled(String s) {
                Log.d("TAG", "onProviderEnabled: ");

            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("TAG", "onProviderDisabled: ");
            }
        });
    }



}
