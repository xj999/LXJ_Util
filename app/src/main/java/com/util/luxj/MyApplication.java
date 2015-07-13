package com.util.luxj;

import android.app.Application;

import org.litepal.LitePalApplication;

/**
 * Created by Luxj on 2015/7/13.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(this);
    }
}
