package com.prashanth.restaurantmenudata;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class RestaurantMenuDataApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        RestaurantMenuDataApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return RestaurantMenuDataApplication.context;
    }
}
