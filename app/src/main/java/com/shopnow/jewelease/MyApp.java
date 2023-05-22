package com.shopnow.jewelease;

import android.app.Application;

public class MyApp extends Application {
    private static MyApp instance;

    public static synchronized MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


}