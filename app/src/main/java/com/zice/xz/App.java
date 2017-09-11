package com.zice.xz;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.zice.xz.greendao.DaoManager;


/**
 * Author Kevin
 * Date 2016/10/26 20:50
 * Email Bridge_passerby@outlook.com
 */
public class App extends Application {
    private static final String TAG = "App";
    private static App _appInst;

    public App() {
        super();
        _appInst = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() TAG -> " + TAG);
        DaoManager.getInstance();
    }

    public static Context getAppContext() {
        return _appInst.getApplicationContext();
    }
}