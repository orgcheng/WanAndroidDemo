package com.example.orgcheng.retrofitdemo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by orgcheng on 18-2-27.
 */

public class App extends Application {
    private static final String TAG = "App";

    public static Context sContext;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.e(TAG, "attachBaseContext: ");
        super.attachBaseContext(base);
        sContext = this;
    }

    public static Context getAppContext() {
        return sContext;
    }
}
