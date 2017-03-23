package com.zice.xz;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Author Kevin
 * Date 2016/10/26 20:50
 * Email Bridge_passerby@outlook.com
 */
public class App extends Application {
    private static final String TAG = "App";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() TAG -> " + TAG);

    }

    public static Context getAppContext() {
        return context;
    }
    
    
    
}