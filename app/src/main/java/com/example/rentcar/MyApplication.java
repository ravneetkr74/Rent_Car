package com.example.rentcar;

import android.app.Application;

import com.example.rentcar.ui.SharedPrefUtil;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initSharedHelper();
    }

    private void initSharedHelper() {
        SharedPrefUtil.init(this);
    }
}
