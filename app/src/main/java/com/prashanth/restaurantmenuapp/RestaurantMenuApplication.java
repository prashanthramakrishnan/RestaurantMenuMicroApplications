package com.prashanth.restaurantmenuapp;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class RestaurantMenuApplication extends Application {

    private static final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Intent proxyIntent = new Intent();
        proxyIntent.setComponent(new ComponentName(
                "com.prashanth.restauranthttpproxyserver", "com.prashanth.restauranthttpproxyserver.RestaurantMenuHTTPProxyService"
        ));
        bindService(proxyIntent, connection, BIND_AUTO_CREATE);
    }

}
